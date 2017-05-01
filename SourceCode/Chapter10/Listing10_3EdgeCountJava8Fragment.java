    int check = toJavaPairRDD(g2.vertices(), tagInteger)
       .join(toJavaPairRDD(g.vertices(), tagInteger))
       .map(t -> t._2._1 - t._2._2)
       .reduce((a,b) -> a+b);
