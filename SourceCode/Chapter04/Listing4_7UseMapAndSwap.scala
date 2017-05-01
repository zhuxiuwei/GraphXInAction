myGraph.aggregateMessages[Int](_.sendToSrc(1),
 _ + _).join(myGraph.vertices).map(_._2.swap).collect
