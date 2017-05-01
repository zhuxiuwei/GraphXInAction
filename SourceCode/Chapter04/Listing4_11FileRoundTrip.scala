myGraph.vertices.saveAsObjectFile("myGraphVertices")
myGraph.edges.saveAsObjectFile("myGraphEdges")
val myGraph2 = Graph(
    sc.objectFile[Tuple2[VertexId,String]]("myGraphVertices"),
    sc.objectFile[Edge[String]]("myGraphEdges"))
