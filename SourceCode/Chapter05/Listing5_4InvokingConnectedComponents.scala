val g = Graph(sc.makeRDD((1L to 7L).map((_,""))),
    sc.makeRDD(Array(Edge(2L,5L,""), Edge(5L,3L,""), Edge(3L,2L,""),
                     Edge(4L,5L,""), Edge(6L,7L,"")))).cache
g.connectedComponents.vertices.map(_.swap).groupByKey.map(_._2).collect
