import org.apache.spark.graphx._
val g = GraphLoader.edgeListFile(sc, System.getProperty("user.home") +
  "/Downloads/facebook/0.edges")
val feat = sc.textFile(System.getProperty("user.home") +
  "/Downloads/facebook/0.feat").map(x =>
  (x.split(" ")(0).toLong, x.split(" ")(78).toInt == 1))
val g2 = g.outerJoinVertices(feat)((vid,vd,u) => u.get)

clusteringCoefficient(g2)

clusteringCoefficient(g2.subgraph(_ => true, (vid,vd) => vd))

clusteringCoefficient(g2.subgraph(_ => true, (vid,vd) => !vd))
