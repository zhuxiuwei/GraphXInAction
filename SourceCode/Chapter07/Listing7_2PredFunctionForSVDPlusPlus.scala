def pred(g:Graph[(Array[Double], Array[Double], Double, Double),Double],
         mean:Double, u:Long, i:Long) = {
  val user = g.vertices.filter(_._1 == u).collect()(0)._2
  val item = g.vertices.filter(_._1 == i).collect()(0)._2
  mean + user._3 + item._3 +
    item._1.zip(user._2).map(x => x._1 * x._2).reduce(_ + _)
}
pred(g, mean, 4L, 13L)
