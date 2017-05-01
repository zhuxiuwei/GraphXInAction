val v = sc.makeRDD(Array((1L,""), (2L,""), (3L,""), (4L,""), (5L,""),
 (6L,""), (7L,""), (8L,"")))
val e = sc.makeRDD(Array(Edge(1L,2L,""), Edge(2L,3L,""), Edge(3L,4L,""),
 Edge(4L,1L,""), Edge(1L,3L,""), Edge(2L,4L,""), Edge(4L,5L,""),
 Edge(5L,6L,""), Edge(6L,7L,""), Edge(7L,8L,""), Edge(8L,5L,""),
 Edge(5L,7L,""), Edge(6L,8L,"")))
lib.LabelPropagation.run(Graph(v,e),5).vertices.collect.
 sortWith(_._1<_._1)
