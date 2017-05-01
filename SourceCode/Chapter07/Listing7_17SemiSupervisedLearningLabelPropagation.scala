import scala.collection.mutable.HashMap

def semiSupervisedLabelPropagation(g:Graph[knnVertex,Double],
                                   maxIterations:Int = 0) = {
  val maxIter = if (maxIterations == 0) g.vertices.count / 2
                else maxIterations

  var g2 = g.mapVertices((vid,vd) => (vd.classNum.isDefined, vd))
  var isChanged = true
  var i = 0

  do {
    val newV =
      g2.aggregateMessages[Tuple2[Option[Int],HashMap[Int,Double]]](
        ctx => {
          ctx.sendToSrc((ctx.srcAttr._2.classNum,
                         if (ctx.dstAttr._2.classNum.isDefined)
                           HashMap(ctx.dstAttr._2.classNum.get->ctx.attr)
                         else
                           HashMap[Int,Double]()))
          if (ctx.srcAttr._2.classNum.isDefined)
            ctx.sendToDst((None,
                           HashMap(ctx.srcAttr._2.classNum.get->ctx.attr)))
        },
        (a1, a2) => {
          if (a1._1.isDefined)
            (a1._1, HashMap[Int,Double]())
          else if (a2._1.isDefined)
            (a2._1, HashMap[Int,Double]())
          else
            (None, a1._2 ++ a2._2.map{
              case (k,v) => k -> (v + a1._2.getOrElse(k,0.0)) })
        }
      )

    val newVClassVoted = newV.map(x => (x._1,
      if (x._2._1.isDefined)
        x._2._1
      else if (x._2._2.size > 0)
        Some(x._2._2.toArray.sortWith((a,b) => a._2 > b._2)(0)._1)
      else None
    ))

    isChanged = g2.vertices.join(newVClassVoted)
                           .map(x => x._2._1._2.classNum != x._2._2)
                           .reduce(_ || _)

    g2 = g2.joinVertices(newVClassVoted)((vid, vd1, u) =>
      (vd1._1, knnVertex(u, vd1._2.pos)))      

    i += 1
  } while (i < maxIter && isChanged)

  g2.mapVertices((vid,vd) => vd._2)
}
