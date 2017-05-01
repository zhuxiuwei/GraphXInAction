import org.apache.spark.graphx._

case class knnVertex(classNum:Option[Int],
                     pos:Array[Double]) extends Serializable {
  def dist(that:knnVertex) = math.sqrt(
    pos.zip(that.pos).map(x => (x._1-x._2)*(x._1-x._2)).reduce(_ + _))
}

def knnGraph(a:Seq[knnVertex], k:Int) = {
  val a2 = a.zipWithIndex.map(x => (x._2.toLong, x._1)).toArray
  val v = sc.makeRDD(a2)
  val e = v.map(v1 => (v1._1, a2.map(v2 => (v2._1, v1._2.dist(v2._2)))
                                .sortWith((e,f) => e._2 < f._2)
                                .slice(1,k+1)
                                .map(_._1)))
           .flatMap(x => x._2.map(vid2 =>
             Edge(x._1, vid2,
                  1 / (1+a2(vid2.toInt)._2.dist(a2(x._1.toInt)._2)))))
  Graph(v,e)
}
