curl --data-binary @/home/cloudera/sjsslashdot/target/scala-2.10/sjsslashdot_2.10-0.1-SNAPSHOT.jar localhost:8090/jars/sd
curl -d "" 'localhost:8090/contexts/sdcontext'
curl -d '{"src":0, "dst":1000}' 'localhost:8090/jobs?appName=sd&classPath=Degrees&context=sdcontext&sync=true&timeout=100'
curl -d '{"src":1000, "dst":1000}' 'localhost:8090/jobs?appName=sd&classPath=Degrees&context=sdcontext&sync=true&timeout=100'
curl -d '{"src":77182, "dst":77359}' 'localhost:8090/jobs?appName=sd&classPath=Degrees&context=sdcontext&sync=true&timeout=100'
