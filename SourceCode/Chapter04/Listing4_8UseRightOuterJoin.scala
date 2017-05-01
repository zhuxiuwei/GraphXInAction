myGraph.aggregateMessages[Int](_.sendToSrc(1),
 _ + _).rightOuterJoin(myGraph.vertices).map(_._2.swap).collect
