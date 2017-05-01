myGraph.mapTriplets(t => (t.attr, t.attr=="is-friends-with" &&
 t.srcAttr.toLowerCase.contains("a"))).triplets.collect
