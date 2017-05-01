import org.apache.spark.graphx._
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD

val trainV = sc.makeRDD(Array((1L, (0,1,false)), (2L, (0,0,false)),
  (3L, (1,0,false)), (4L, (0,0,false)), (5L, (0,0,false)),
  (6L, (0,0,false)), (7L, (0,0,false)), (8L, (0,0,false)),
  (9L, (0,1,false)), (10L,(0,0,false)), (11L,(5,2,true)),
  (12L,(0,0,true)),  (13L,(1,0,false))))

val trainE = sc.makeRDD(Array(Edge(1L,9L,""), Edge(2L,3L,""),
  Edge(3L,10L,""), Edge(4L,9L,""), Edge(4L,10L,""), Edge(5L,6L,""),
  Edge(5L,11L,""), Edge(5L,12L,""), Edge(6L,11L,""), Edge(6L,12L,""),
  Edge(7L,8L,""), Edge(7L,11L,""), Edge(7L,12L,""), Edge(7L,13L,""),
  Edge(8L,11L,""), Edge(8L,12L,""), Edge(8L,13L,""), Edge(9L,2L,""),
  Edge(9L,13L,""), Edge(10L,13L,""), Edge(12L,9L,"")))

val trainG = Graph(trainV, trainE)
