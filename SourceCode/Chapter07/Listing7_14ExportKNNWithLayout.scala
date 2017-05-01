import java.awt.Color
def toGexfWithViz(g:Graph[knnVertex,Double], scale:Double) = {
  val colors = Array(Color.red, Color.blue, Color.yellow, Color.pink,
                     Color.magenta, Color.green, Color.darkGray)
  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
  "<gexf xmlns=\"http://www.gexf.net/1.2draft\" " +
        "xmlns:viz=\"http://www.gexf.net/1.1draft/viz\" " +
        "version=\"1.2\">\n" +
  "  <graph mode=\"static\" defaultedgetype=\"directed\">\n" +
  "    <nodes>\n" +
  g.vertices.map(v =>
    "      <node id=\"" + v._1 + "\" label=\"" + v._1 + "\">\n" +
    "        <viz:position x=\"" + v._2.pos(0) * scale +
              "\" y=\"" + v._2.pos(1) * scale + "\" />\n" +
    (if (v._2.classNum.isDefined)
       "        <viz:color r=\"" + colors(v._2.classNum.get).getRed +
                 "\" g=\"" + colors(v._2.classNum.get).getGreen +
                 "\" b=\"" + colors(v._2.classNum.get).getBlue + "\" />\n"
     else "") +
    "      </node>\n").collect.mkString +
  "    </nodes>\n" +
  "    <edges>\n" +
  g.edges.map(e => "      <edge source=\"" + e.srcId +
                   "\" target=\"" + e.dstId + "\" label=\"" + e.attr +
                   "\" />\n").collect.mkString +
  "    </edges>\n" +
  "  </graph>\n" +
  "</gexf>"
}
