val g = Pregel(myGraph.mapVertices((vid,vd) => 0), 0,
               activeDirection = EdgeDirection.Out)(
               (id:VertexId,vd:Int,a:Int) => math.max(vd,a),
               (et:EdgeTriplet[Int,String]) =>
                    Iterator((et.dstId, et.srcAttr+1)),
               (a:Int,b:Int) => math.max(a,b))
g.vertices.collect
