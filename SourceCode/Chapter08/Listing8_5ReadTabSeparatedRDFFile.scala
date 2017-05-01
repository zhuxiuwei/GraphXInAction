def readRdf(sc:org.apache.spark.SparkContext, filename:String) = {
  val r = sc.textFile(filename).map(_.split("\t"))
  val v = r.map(_(1)).union(r.map(_(3))).distinct.zipWithIndex
  Graph(v.map(_.swap),
        r.map(x => (x(1),(x(2),x(3))))
         .join(v)
         .map(x => (x._2._1._2,(x._2._2,x._2._1._1)))
         .join(v)
         .map(x => new Edge(x._2._1._1, x._2._2, x._2._1._2)))
}
