import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object helloworld {
  def main(args: Array[String]) {
    val sc = new SparkContext(new SparkConf().setMaster("local")
                                             .setAppName("helloworld"))
    val r = sc.makeRDD(Array("Hello", "World"))
    r.foreach(println(_))
    sc.stop
  }
}
