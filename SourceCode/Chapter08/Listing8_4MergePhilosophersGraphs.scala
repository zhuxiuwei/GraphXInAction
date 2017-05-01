val philosophers = Graph(
sc.makeRDD(Seq(
    (1L, "Aristotle"),(2L,"Plato"),(3L,"Socrates"),(4L,"male"))),
sc.makeRDD(Seq(
    Edge(2L,1L,"Influences"),
    Edge(3L,2L,"Influences"),
    Edge(3L,4L,"hasGender"))))

val rdfGraph = Graph(   
    sc.makeRDD(Seq(
        (1L,"wordnet_philosophers"),(2L,"Aristotle"),
        (3L,"Plato"),(4L,"Socrates"))),
    sc.makeRDD(Seq(
        Edge(2L,1L,"rdf:type"),
        Edge(3L,1L,"rdf:type"),
        Edge(4L,1L,"rdf:type"))))

val combined = mergeGraphs(philosophers, rdfGraph)

combined.triplets.foreach(
       t => println(s"${t.srcAttr} --- ${t.attr} ---> ${t.dstAttr}"))
