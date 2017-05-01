absent.sqlContext.sql(
  "SELECT v1.attr, " +
  "       v3.attr, " +
  "       SUM(v1.outdegree * v2.outdegree * v3.outdegree) AS score " +
  "FROM   a " +
            "WHERE  v1.id=7662 " +
            "GROUP BY v1.attr, v3.attr " +
            "ORDER BY score DESC").collect
