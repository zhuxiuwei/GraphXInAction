import org.apache.spark.graphx._
def dijkstra[VD](g:Graph[VD,Double], origin:VertexId) = {
  var g2 = g.mapVertices(
    (vid,vd) => (false, if (vid == origin) 0 else Double.MaxValue,
                 List[VertexId]()))

  for (i <- 1L to g.vertices.count-1) {
    val currentVertexId =
      g2.vertices.filter(!_._2._1)
        .fold((0L,(false,Double.MaxValue,List[VertexId]())))((a,b) =>
           if (a._2._2 < b._2._2) a else b)
        ._1

    val newDistances = g2.aggregateMessages[(Double,List[VertexId])](
        ctx => if (ctx.srcId == currentVertexId)
                 ctx.sendToDst((ctx.srcAttr._2 + ctx.attr,
                                ctx.srcAttr._3 :+ ctx.srcId)),
        (a,b) => if (a._1 < b._1) a else b)

    g2 = g2.outerJoinVertices(newDistances)((vid, vd, newSum) => {
      val newSumVal =
        newSum.getOrElse((Double.MaxValue,List[VertexId]()))
      (vd._1 || vid == currentVertexId,
       math.min(vd._2, newSumVal._1),
       if (vd._2 < newSumVal._1) vd._3 else newSumVal._2)})
  }
  
  g.outerJoinVertices(g2.vertices)((vid, vd, dist) =>
    (vd, dist.getOrElse((false,Double.MaxValue,List[VertexId]()))
             .productIterator.toList.tail))
}
