myGraph.aggregateMessages[Int](_.sendToSrc(1),
 _ + _).rightOuterJoin(myGraph.vertices).map(
 x => (x._2._2, x._2._1.getOrElse(0))).collect
