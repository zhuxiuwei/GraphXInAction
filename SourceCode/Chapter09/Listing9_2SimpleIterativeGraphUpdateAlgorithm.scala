val iterations = 500
var g = Graph.fromEdges(sc.makeRDD(
       Seq(Edge(1L,3L,1),Edge(2L,4L,1),Edge(3L,4L,1))),1)
for (i <- 1 to iterations) {
  println("Iteration: " + i)
  val newGraph: Graph[Int, Int] = 
      g.mapVertices((vid,vd)  => (vd * i)/17)
  g = g.outerJoinVertices[Int, Int](newGraph.vertices) { 
                   (vid, vd, newData) => newData.getOrElse(0) 
      }
}
g.vertices.collect.foreach(println)
