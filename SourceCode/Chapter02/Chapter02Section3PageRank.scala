import org.apache.spark.graphx._
val graph = GraphLoader.edgeListFile(sc, "cit-HepTh.txt")
val v = graph.pageRank(0.001).vertices
v.reduce((a,b) => if (a._2 > b._2) a else b)
