import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.module.scala.DefaultScalaModule

myGraph.vertices.mapPartitions(vertices => {
    val mapper = new com.fasterxml.jackson.databind.ObjectMapper()
    mapper.registerModule(DefaultScalaModule)
    val writer = new java.io.StringWriter()
    vertices.map(v => {writer.getBuffer.setLength(0)
                       mapper.writeValue(writer, v)
                       writer.toString})
}).coalesce(1,true).saveAsTextFile("myGraphVertices")

myGraph.edges.mapPartitions(edges => {
    val mapper = new com.fasterxml.jackson.databind.ObjectMapper();
    mapper.registerModule(DefaultScalaModule)
    val writer = new java.io.StringWriter()
    edges.map(e => {writer.getBuffer.setLength(0)
                    mapper.writeValue(writer, e)
                    writer.toString})
}).coalesce(1,true).saveAsTextFile("myGraphEdges")

val myGraph2 = Graph(
    sc.textFile("myGraphVertices").mapPartitions(vertices => {
        val mapper = new com.fasterxml.jackson.databind.ObjectMapper()
        mapper.registerModule(DefaultScalaModule)
        vertices.map(v => {
            val r = mapper.readValue[Tuple2[Integer,String]](v,
                new TypeReference[Tuple2[Integer,String]]{})
            (r._1.toLong, r._2)
        })
    }),
    sc.textFile("myGraphEdges").mapPartitions(edges => {
        val mapper = new com.fasterxml.jackson.databind.ObjectMapper()
        mapper.registerModule(DefaultScalaModule)
        edges.map(e => mapper.readValue[Edge[String]](e,
            new TypeReference[Edge[String]]{}))
    })
)
