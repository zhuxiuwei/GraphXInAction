val gf = GraphFrame.fromGraphX(myGraph)
gf.find("(u)-[e1]->(v); (v)-[e2]->(w)")
  .filter("e1.attr = 'is-friends-with' AND " +
          "e2.attr = 'is-friends-with' AND " +
          "u.attr='Ann'")
  .select("w.attr")
  .collect
  .map(_(0).toString)
