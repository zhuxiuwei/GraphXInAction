val gf = readRdf(sc, "yagoFacts.tsv").subgraph(_.attr == "<exports>")
val e = gf.edges.map(e => Edge(e.srcId, e.dstId, 1.0))
val (gs,mean) = lib.SVDPlusPlus.run(e,
      new lib.SVDPlusPlus.Conf(2,10,0,5,0.007,0.007,0.005,0.015))
