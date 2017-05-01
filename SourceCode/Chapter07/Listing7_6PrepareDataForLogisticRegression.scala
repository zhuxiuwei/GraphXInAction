import org.apache.spark.graphx.lib.PageRank
import org.apache.spark.mllib.linalg.DenseVector
import org.apache.spark.mllib.regression.LabeledPoint
def augment(g:Graph[Tuple3[Int,Int,Boolean],String]) =
  g.vertices.join(
    PageRank.run(trainG, 1).vertices.join(
      PageRank.run(trainG, 5).vertices
    ).map(x => (x._1,x._2._2/x._2._1))
  ).map(x => LabeledPoint(
    if (x._2._1._3) 1 else 0,
    new DenseVector(Array(x._2._1._1, x._2._1._2, x._2._2))))
