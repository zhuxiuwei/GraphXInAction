def pred(v:Map[VertexId, (Array[Double], Array[Double], Double, Double)],
         mean:Double, u:Long, i:Long) = {
  val user = v.getOrElse(u, (Array(0.0), Array(0.0), 0.0, 0.0))
  val item = v.getOrElse(i, (Array(0.0), Array(0.0), 0.0, 0.0))
  mean + user._3 + item._3 + item._1.zip(user._2).map(
    x => x._1*x._2).reduce(_ + _)
}

def vertexMap(g:Graph[(Array[Double], Array[Double],
                       Double, Double),Double]) =
  g.vertices.collect.map(v => v._1 -> v._2).toMap
