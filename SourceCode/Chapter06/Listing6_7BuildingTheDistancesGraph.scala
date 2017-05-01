val dist = sc.textFile("animal_distances.txt") 
val verts = dist.map(_.split(",")(0)).distinct. \\#A 
    map(x => (x.hashCode.toLong,x)) \\#B 
val edges = dist.map(x => x.split(",")). 
    map(x => Edge(x(0).hashCode.toLong, \\#B 
                 x(1).hashCode.toLong, \\#B 
                 x(2).toDouble)) 
val distg = Graph(verts, edges) 
val mst = minSpanningTree(distg) \\#C 
val pw = new java.io.PrintWriter("animal_taxonomy.gexf") 
pw.write(toGexf(mst)) 
pw.close 
