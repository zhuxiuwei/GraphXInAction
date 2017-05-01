val in = readRdfDf(sc, "yagoFactsInfluences.tsv")

in.edges.registerTempTable("e")
in.vertices.registerTempTable("v")

val in2 = GraphFrame(in.vertices.sqlContext.sql(
                       "SELECT v.id," +
                       "       FIRST(v.attr) AS attr," +
                       "       COUNT(*) AS outdegree " +
                       "FROM   v " +
                       "JOIN   e " +
                       "  ON   v.id=e.src " +
                       "GROUP BY v.id").cache,
                     in.edges)

val absent = in2.find("(v1)-[]->(v2); (v2)-[]->(v3); !(v1)-[]->(v3)")
absent.registerTempTable("a")

val present = in2.find("(v1)-[]->(v2); (v2)-[]->(v3); (v1)-[]->(v3)")
present.registerTempTable("p")

absent.sqlContext.sql(
  "SELECT v1 an," +
  "       SUM(v1.outdegree * v2.outdegree * v3.outdegree) AS ac " +
  "FROM   a " +
  "GROUP BY v1").registerTempTable("aa")

present.sqlContext.sql(
  "SELECT v1 pn," +
  "       SUM(v1.outdegree * v2.outdegree * v3.outdegree) AS pc " +
  "FROM   p " +
  "GROUP BY v1").registerTempTable("pa")

absent.sqlContext.sql("SELECT an," +
                      "       ac * pc/(ac+pc) AS score " +
                      "FROM   aa " +
                      "JOIN   pa" +
                      "  ON   an=pn " +
                      "ORDER BY score DESC").show
