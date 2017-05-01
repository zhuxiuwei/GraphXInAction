g.stronglyConnectedComponents(10).vertices.map(_.swap).groupByKey.
    map(_._2).collect
