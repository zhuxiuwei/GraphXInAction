val vertices = sc.makeRDD(Seq(
	(1L, "Ann"), (2L, "Bill"), (3L, "Charles"), (4L, "Dianne")))
val edges = sc.makeRDD(Seq(
	Edge(1L,2L, "is-friends-with"),Edge(1L,3L, "is-friends-with"),
	Edge(4L,1L, "has-blocked"),Edge(2L,3L, "has-blocked"),
	Edge(3L,4L, "has-blocked")))  
val originalGraph = Graph(vertices, edges)
val subgraph = originalGraph.subgraph(et => et.attr == "is-friends-with")

// show vertices of subgraph – includes Dianne
subgraph.vertices.foreach(println)

// now call removeSingletons and show the resulting vertices
removeSingletons(subgraph).vertices.foreach(println)
