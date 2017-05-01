case class Person(name: String, age: Int)

val conf = new SparkConf()
conf.set("spark.serializer",
         "org.apache.spark.serializer.KryoSerializer")
conf.registerKryoClasses(Array(classOf[Person]))
val sc = new SparkContext(conf)
val rdd = sc.makeRDD(1 to 1000000).
             map(el => Person("John Smith", 42))
rdd.persist(StorageLevel.MEMORY_ONLY_SER) 
rdd.count
