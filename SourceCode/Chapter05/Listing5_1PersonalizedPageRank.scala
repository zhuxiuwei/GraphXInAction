import org.apache.spark.graphx._
val g = GraphLoader.edgeListFile(sc, "cit-HepTh.txt")
g.personalizedPageRank(9207016, 0.001)
 .vertices
 .filter(_._1 != 9207016)
 .reduce((a,b) => if (a._2 > b._2) a else b)
