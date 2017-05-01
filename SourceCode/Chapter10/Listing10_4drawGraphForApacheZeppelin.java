import org.apache.spark.graphx._
import scala.reflect.ClassTag
def drawGraph[VD:ClassTag,ED:ClassTag](g:Graph[VD,ED]) = {
val u = java.util.UUID.randomUUID
val v = g.vertices.collect.map(_._1)
println("""%html
<div id='a""" + u + """' style='width:960px; height:500px'></div>
<style>
.node circle { fill: gray; }
.node text { font: 10px sans-serif;
             text-anchor: middle;
             fill: white; }
line.link { stroke: gray;
            stroke-width: 1.5px; }
</style>
<script src="//d3js.org/d3.v3.min.js"></script>
<script>
var width = 960, height = 500;

var svg = d3.select("#a""" + u + """").append("svg")
    .attr("width", width).attr("height", height);

var nodes = [""" + v.map("{id:" + _ + "}").mkString(",") + """];
var links = [""" + g.edges.collect.map(
  e => "{source:nodes[" + v.indexWhere(_ == e.srcId) + "],target:nodes[" +
       v.indexWhere(_ == e.dstId) + "]}").mkString(",") + """];

var link = svg.selectAll(".link").data(links);
link.enter().insert("line", ".node").attr("class", "link");

var node = svg.selectAll(".node").data(nodes);
var nodeEnter = node.enter().append("g").attr("class", "node")

nodeEnter.append("circle").attr("r", 8);

nodeEnter.append("text").attr("dy", "0.35em")
         .text(function(d) { return d.id; });

d3.layout.force().linkDistance(50).charge(-200).chargeDistance(300)
   .friction(0.95).linkStrength(0.5).size([width, height])
   .on("tick", function() {
      link.attr("x1", function(d) { return d.source.x; })
          .attr("y1", function(d) { return d.source.y; })
          .attr("x2", function(d) { return d.target.x; })
          .attr("y2", function(d) { return d.target.y; });
      node.attr("transform", function(d) {
        return "translate(" + d.x + "," + d.y + ")";
      });
   }).nodes(nodes).links(links).start();
</script>
""")
}
