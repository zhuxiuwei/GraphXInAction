import org.apache.spark.sql.Row
import org.apache.spark.sql.types._
def readRdfDf(sc:org.apache.spark.SparkContext, filename:String) = {
  val r = sc.textFile(filename).map(_.split("\t"))
  val v = r.map(_(1)).union(r.map(_(3))).distinct.zipWithIndex.map(
                   x => Row(x._2,x._1))
  // We must have an "id" column in the vertices DataFrame;
  // everything else is just properties we assign to the vertices
  val stv = StructType(StructField("id",LongType) ::
                       StructField("attr",StringType) :: Nil)
  val sqlContext = new org.apache.spark.sql.SQLContext(sc) 
  val vdf = sqlContext.createDataFrame(v,stv)
  vdf.registerTempTable("v")
  val str = StructType(StructField("rdfId",StringType) ::
                       StructField("subject",StringType) ::
                       StructField("predicate",StringType) ::
                       StructField("object",StringType) :: Nil)
  sqlContext.createDataFrame(r.map(Row.fromSeq(_)),str)
            .registerTempTable("r")
  // We must have an "src" and "dst" columns in the edges DataFrame;
  // everything else is just properties we assign to the edges
  val edf = sqlContext.sql("SELECT vsubject.id AS src," +
                           "       vobject.id AS dst," +
                           "       predicate AS attr " +
                           "FROM   r " +
                           "JOIN   v AS vsubject" +
                           "  ON   subject=vsubject.attr " +
                           "JOIN   v AS vobject" +
                           "  ON   object=vobject.attr")
  GraphFrame(vdf,edf)
}
