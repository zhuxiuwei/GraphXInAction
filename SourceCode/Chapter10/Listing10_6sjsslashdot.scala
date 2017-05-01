import org.apache.spark.SparkContext
import org.apache.spark.graphx._
import org.apache.spark.graphx.lib.ShortestPaths

import com.typesafe.config.Config

import spark.jobserver._

object Degrees extends SparkJob {
  val filename = System.getProperty("user.home") + "/soc-Slashdot0811.txt"
  var g:Option[Graph[Int,Int]] = None

  override def runJob(sc:SparkContext, config:Config) = {
    if (!g.isDefined)
      g = Some(GraphLoader.edgeListFile(sc, filename).cache)

    val src = config.getString("src").toInt

    if (g.get.vertices.filter(_._1 == src).isEmpty)
      -1
    else {
      val r = ShortestPaths.run(g.get, Array(src))
                           .vertices
                           .filter(_._1 == config.getString("dst").toInt)

      if (r.isEmpty || r.first._2.toList.isEmpty) -1
      else r.first._2.toList.head._2
    }
  }

  override def validate(sc:SparkContext, config:Config) = SparkJobValid
}
