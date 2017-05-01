import org.apache.spark.graphx._

val edges = sc.makeRDD(Array(
  Edge(1L,11L,5.0),Edge(1L,12L,4.0),Edge(2L,12L,5.0),
  Edge(2L,13L,5.0),Edge(3L,11L,5.0),Edge(3L,13L,2.0),
  Edge(4L,11L,4.0),Edge(4L,12L,4.0)))

val conf = new lib.SVDPlusPlus.Conf(2,10,0,5,0.007,0.007,0.005,0.015)

val (g,mean) = lib.SVDPlusPlus.run(edges, conf)
