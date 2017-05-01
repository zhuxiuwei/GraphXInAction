val gc = removeSingletons(gf.subgraph(et => et.srcAttr == "<Canada>"))
val vr = e.map(x => (x.dstId,""))
          .distinct
          .subtractByKey(gc.vertices)
          .map(_._1)
