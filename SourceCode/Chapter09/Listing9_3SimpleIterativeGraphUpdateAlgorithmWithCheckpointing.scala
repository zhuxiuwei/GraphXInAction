sc.setCheckpointDir("/tmp/spark-checkpoint")
var updateCount = 0
val checkpointInterval = 50

def update(newData: Graph[Int, Int]): Unit = {
  newData.persist()
  updateCount += 1
  if (updateCount % checkpointInterval == 0) {
    newData.checkpoint()
  }
}

val iterations = 500
var g = Graph.fromEdges(sc.makeRDD(
       Seq(Edge(1L,3L,1),Edge(2L,4L,1),Edge(3L,4L,1))),1)
update(g)
g.vertices.count
for (i <- 1 to iterations) {
  println("Iteration: " + i)
  val newGraph: Graph[Int, Int] = g.mapVertices((vid,vd)  => (vd * i)/17)
  g = g.outerJoinVertices[Int, Int](newGraph.vertices) { 
      (vid, vd, newData) => newData.getOrElse(0) }
  update(g)
  g.vertices.count
}
g.vertices.collect.foreach(println)
