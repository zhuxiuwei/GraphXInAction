import org.apache.spark.graphx._
import org.apache.spark.{SparkContext, SparkConf}

import edu.berkeley.cs.amplab.spark.indexedrdd.IndexedRDD
import edu.berkeley.cs.amplab.spark.indexedrdd.IndexedRDD._

object readrdf {
  def readRdfIndexed(sc:SparkContext, filename:String) = {
    val r = sc.textFile(filename).map(_.split("\t"))
    val v = IndexedRDD(r.map(_(1)).union(r.map(_(3))).distinct
                        .zipWithIndex)
    Graph(v.map(_.swap),
          IndexedRDD(IndexedRDD(r.map(x => (x(1),(x(2),x(3)))))
           .innerJoin(v)((id, a, b) => (a,b))
           .map(x => (x._2._1._2,(x._2._2,x._2._1._1))))
           .innerJoin(v)((id, a, b) => (a,b))
           .map(x => new Edge(x._2._1._1, x._2._2, x._2._1._2)))
  }

  def main(args: Array[String]) {
    val sc = new SparkContext(
      new SparkConf().setMaster("local").setAppName("readrdf"))
    val t0 = System.currentTimeMillis
    val r = readRdf(sc, “yagoFacts.tsv")
    println("#edges=" + r.edges.count +
            " #vertices=" + r.vertices.count)
    val t1 = System.currentTimeMillis
    println("Elapsed: " + ((t1-t0) / 1000) + "sec")
    sc.stop
  }
}
