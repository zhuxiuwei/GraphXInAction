def greedy[VD](g:Graph[VD,Double], origin:VertexId) = {
  var g2 = g.mapVertices((vid,vd) => vid == origin)
            .mapTriplets(et => (et.attr,false))
  var nextVertexId = origin
  var edgesAreAvailable = true

  do {
    type tripletType = EdgeTriplet[Boolean,Tuple2[Double,Boolean]]

    val availableEdges =
      g2.triplets
        .filter(et => !et.attr._2
                      && (et.srcId == nextVertexId && !et.dstAttr
                          || et.dstId == nextVertexId && !et.srcAttr))

    edgesAreAvailable = availableEdges.count > 0

    if (edgesAreAvailable) {
      val smallestEdge = availableEdges
          .min()(new Ordering[tripletType]() {
             override def compare(a:tripletType, b:tripletType) = {
               Ordering[Double].compare(a.attr._1,b.attr._1)
             }
           })

      nextVertexId = Seq(smallestEdge.srcId, smallestEdge.dstId)
                     .filter(_ != nextVertexId)(0)

      g2 = g2.mapVertices((vid,vd) => vd || vid == nextVertexId)
             .mapTriplets(et => (et.attr._1,
                                 et.attr._2 ||
                                   (et.srcId == smallestEdge.srcId
                                    && et.dstId == smallestEdge.dstId)))
    }
  } while(edgesAreAvailable)
  
  g2
}

greedy(myGraph,1L).triplets.filter(_.attr._2).map(et=>(et.srcId, et.dstId))
                  .collect
