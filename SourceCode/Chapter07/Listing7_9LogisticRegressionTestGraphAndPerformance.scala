val testV = sc.makeRDD(Array((1L, (0,1,false)), (2L, (0,0,false)),
  (3L, (1,0,false)), (4L, (5,4,true)), (5L, (0,1,false)),
  (6L, (0,0,false)), (7L, (1,1,true))))

val testE = sc.makeRDD(Array(Edge(1L,5L,""), Edge(2L,5L,""),
  Edge(3L,6L,""), Edge(4L,6L,""), Edge(5L,7L,""), Edge(6L,7L,"")))
  
perf(augment(Graph(testV,testE)))
