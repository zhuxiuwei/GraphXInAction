def knnPredict[E](g:Graph[knnVertex,E],pos:Array[Double]) =
  g.vertices
   .filter(_._2.classNum.isDefined)
   .map(x => (x._2.classNum.get, x._2.dist(knnVertex(None,pos))))
   .min()(new Ordering[Tuple2[Int,Double]] {
     override def compare(a:Tuple2[Int,Double],
                          b:Tuple2[Int,Double]): Int =
       a._2.compare(b._2)
    })
   ._1
