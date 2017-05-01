import org.apache.spark.graphx._
val g = GraphLoader.edgeListFile(sc, "soc-Slashdot0811.txt").cache
val g2 = Graph(g.vertices, g.edges.map(e =>
        if (e.srcId < e.dstId) e else new Edge(e.dstId, e.srcId, e.attr))).
    partitionBy(PartitionStrategy.RandomVertexCut)
(0 to 6).map(i => g2.subgraph(vpred =
        (vid,_) => vid >= i*10000 && vid < (i+1)*10000).
    triangleCount.vertices.map(_._2).reduce(_ + _))
