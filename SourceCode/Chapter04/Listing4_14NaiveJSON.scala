myGraph.vertices.map(x => {
    val mapper = new com.fasterxml.jackson.databind.ObjectMapper()
    mapper.registerModule(
        com.fasterxml.jackson.module.scala.DefaultScalaModule)
    val writer = new java.io.StringWriter()
    mapper.writeValue(writer, x)
    writer.toString
}).coalesce(1,true).saveAsTextFile("myGraphVertices")
