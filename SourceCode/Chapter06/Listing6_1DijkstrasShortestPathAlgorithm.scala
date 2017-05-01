import org.apache.spark.graphx._
def dijkstra[VD](g:Graph[VD,Double], origin:VertexId) = {
  var g2 = g.mapVertices(
    (vid,vd) => (false, if (vid == origin) 0 else Double.MaxValue))

  for (i <- 1L to g.vertices.count-1) {
    val currentVertexId =
      g2.vertices.filter(!_._2._1)
        .fold((0L,(false,Double.MaxValue)))((a,b) =>
           if (a._2._2 < b._2._2) a else b)
        ._1
    
    val newDistances = g2.aggregateMessages[Double](
        ctx => if (ctx.srcId == currentVertexId)
                 ctx.sendToDst(ctx.srcAttr._2 + ctx.attr),
        (a,b) => math.min(a,b))

    g2 = g2.outerJoinVertices(newDistances)((vid, vd, newSum) =>
      (vd._1 || vid == currentVertexId,
       math.min(vd._2, newSum.getOrElse(Double.MaxValue))))
  }
  
  g.outerJoinVertices(g2.vertices)((vid, vd, dist) =>
    (vd, dist.getOrElse((false,Double.MaxValue))._2))
}
