import org.apache.spark.graphx._ 

def mergeGraphs(g1:Graph[String,String], g2:Graph[String,String]) = {
  val v = g1.vertices.map(_._2).union(g2.vertices.map(_._2)).distinct
                     .zipWithIndex
  def edgesWithNewVertexIds(g:Graph[String,String]) =
    g.triplets
     .map(et => (et.srcAttr, (et.attr,et.dstAttr)))
     .join(v)
     .map(x => (x._2._1._2, (x._2._2,x._2._1._1)))
     .join(v)
     .map(x => new Edge(x._2._1._1,x._2._2,x._2._1._2))
  Graph(v.map(_.swap),
        edgesWithNewVertexIds(g1).union(edgesWithNewVertexIds(g2)))
}
