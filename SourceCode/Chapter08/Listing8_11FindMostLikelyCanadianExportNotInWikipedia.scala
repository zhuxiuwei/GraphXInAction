val vm = vertexMap(gs)
val cid = gf.vertices.filter(_._2 == "<Canada>").first._1
val r = vr.map(v => (v,pred(vm,mean,cid,v)))

val maxKey = r.max()(new Ordering[Tuple2[VertexId, Double]]() {
  override def compare(x: (VertexId, Double), y: (VertexId, Double)): Int = 
      Ordering[Double].compare(x._2, y._2)
})._1

gf.vertices.filter(_._1 == maxKey).collect
