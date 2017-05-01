import scala.util.Random
Random.setSeed(17L)
val n = 10
val a = (1 to n*2).map(i => {
  val x = Random.nextDouble;
  if (i <= n)
    knnVertex(if (i % n == 0) Some(0) else None, Array(x*50,
      20 + (math.sin(x*math.Pi) + Random.nextDouble / 2) * 25))
  else
    knnVertex(if (i % n == 0) Some(1) else None, Array(x*50 + 25,
      30 - (math.sin(x*math.Pi) + Random.nextDouble / 2) * 25))
})
