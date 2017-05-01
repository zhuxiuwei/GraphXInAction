wget http://repo1.maven.org/maven2/com/fasterxml/jackson/module/jackson-module-scala_2.10/2.4.4/jackson-module-scala_2.10-2.4.4.jar
wget http://repo1.maven.org/maven2/com/google/guava/guava/14.0.1/guava-14.0.1.jar
./spark-shell --jars jackson-module-scala_2.10-2.4.4.jar,guava-14.0.1.jar
