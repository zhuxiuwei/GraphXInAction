import scala.reflect.ClassTag

def removeSingletons[VD:ClassTag,ED:ClassTag](g:Graph[VD,ED]) =
  Graph(g.triplets.map(et => (et.srcId,et.srcAttr))
                  .union(g.triplets.map(et => (et.dstId,et.dstAttr)))
                  .distinct,
        g.edges)
