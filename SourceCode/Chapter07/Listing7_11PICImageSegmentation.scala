import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.mllib.clustering.PowerIterationClustering
import org.apache.spark.graphx._

import java.awt.image.BufferedImage
import java.awt.image.DataBufferInt
import java.awt.Color
import java.io.File

import javax.imageio.ImageIO

object PIC {
  def main(args: Array[String]) {
    val sc = new SparkContext(new SparkConf().setMaster("local")
                                             .setAppName("PIC"))
    val im = ImageIO.read(new File("105053.jpg"))
    val ims = im.getScaledInstance(im.getWidth/8, im.getHeight/8,
                                   java.awt.Image.SCALE_AREA_AVERAGING)
    val width = ims.getWidth(null)
    val height = ims.getHeight(null)
    val bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    bi.getGraphics.drawImage(ims, 0, 0, null)
    val r = sc.makeRDD(bi.getData.getDataBuffer
                         .asInstanceOf[DataBufferInt].getData)
              .zipWithIndex.cache
    val g = Graph.fromEdges(r.cartesian(r).cache.map(x => {
      def toVec(a:Tuple2[Int,Long]) = {
        val c = new Color(a._1)
        Array[Double](c.getRed, c.getGreen, c.getBlue)
      }
      def cosineSimilarity(u:Array[Double], v:Array[Double]) = {
        val d = Math.sqrt(u.map(a => a*a).sum * v.map(a => a*a).sum)
        if (d == 0.0) 0.0 else
        u.zip(v).map(a => a._1 * a._2).sum / d
      }
      Edge(x._1._2, x._2._2, cosineSimilarity(toVec(x._1), toVec(x._2)))
    }).filter(e => e.attr > 0.5), 0.0).cache
    val m = new PowerIterationClustering().run(g)
    val colors = Array(Color.white.getRGB, Color.black.getRGB)
    val bi2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    m.assignments
     .map(a => (a.id/width, (a.id%width, colors(a.cluster))))
     .groupByKey
     .map(a => (a._1, a._2.toList.sortBy(_._1).map(_._2).toArray))
     .collect
     .foreach(x => bi2.setRGB(0, x._1.toInt, width, 1, x._2, 0, width))
    ImageIO.write(bi2, "PNG", new File("out.png"));
    sc.stop
  }
}
