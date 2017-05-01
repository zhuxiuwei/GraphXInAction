def knnGraphApprox(a:Seq[knnVertex], k:Int) = {
  val a2 = a.zipWithIndex.map(x => (x._2.toLong, x._1)).toArray
  val v = sc.makeRDD(a2)
  val n = 3
  val minMax =
    v.map(x => (x._2.pos(0), x._2.pos(0), x._2.pos(1), x._2.pos(1)))
     .reduce((a,b) => (math.min(a._1,b._1), math.max(a._2,b._2),
                       math.min(a._3,b._3), math.max(a._4,b._4)))
  val xRange = minMax._2 - minMax._1
  val yRange = minMax._4 - minMax._3

  def calcEdges(offset: Double) =
    v.map(x => (math.floor((x._2.pos(0) - minMax._1)
                           / xRange * (n-1) + offset) * n
                  + math.floor((x._2.pos(1) - minMax._3)
                               / yRange * (n-1) + offset),
                x))
     .groupByKey(n*n)
     .mapPartitions(ap => {
       val af = ap.flatMap(_._2).toList
       af.map(v1 => (v1._1, af.map(v2 => (v2._1, v1._2.dist(v2._2)))
                              .toArray
                              .sortWith((e,f) => e._2 < f._2)
                              .slice(1,k+1)
                              .map(_._1)))
            .flatMap(x => x._2.map(vid2 => Edge(x._1, vid2,
               1 / (1+a2(vid2.toInt)._2.dist(a2(x._1.toInt)._2)))))
            .iterator
      })

  val e = calcEdges(0.0).union(calcEdges(0.5))
                        .distinct
                        .map(x => (x.srcId,x))
                        .groupByKey
                        .map(x => x._2.toArray
                                   .sortWith((e,f) => e.attr > f.attr)
                                   .take(k))
                        .flatMap(x => x)

  Graph(v,e)
}
