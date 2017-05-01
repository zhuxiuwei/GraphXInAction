import org.graphframes._
val gf = GraphFrame.fromGraphX(g2)
def time[A](f: => A) = {
  val s = System.nanoTime
  val ret = f
  println("time: " + (System.nanoTime-s)/1e9 + "sec")
  ret
}

time { g2.triangleCount.vertices.map(_._2).reduce(_ + _) }

time { gf.triangleCount.run.vertices.groupBy().sum("count")
         .collect()(0)(0).asInstanceOf[Long] }
