def sendMsg(ec: EdgeContext[Int,String,Int]): Unit = {
  ec.sendToDst(ec.srcAttr+1)
}

def mergeMsg(a: Int, b: Int): Int = {
  math.max(a,b)
}

def propagateEdgeCount(g:Graph[Int,String]):Graph[Int,String] = {    
  val verts = g.aggregateMessages[Int](sendMsg, mergeMsg)
  val g2 = Graph(verts, g.edges)
  val check = g2.vertices.join(g.vertices).
       map(x => x._2._1 – x._2._2).
       reduce(_ + _)
  if (check > 0)
    propagateEdgeCount(g2)
  else
    g
}
