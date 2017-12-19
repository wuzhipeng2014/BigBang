/**
  * Created by zhipengwu on 17-12-19.
  */
object HelloTest {
  // Parse input line
  def parseLine(line: String) = {
    def parseFeature(feature: String) = {
      feature.split(":") match {
        case Array(k, v) => (k, v.toDouble)
      }
    }

    val bits = line.split(" ")
    val id = bits.head
    val features = bits.tail.map(parseFeature).toMap
    (id, features)
  }

  // Compute dot product between to dicts
  def dotProduct(x: Map[String, Double], y: Map[String, Double]): Double = ???

  // Compute norm of dict
  def norm(x: Map[String, Double]): Double = ???

  // Compute cosine similarity
  def sparseCosine(x: Map[String, Double], y: Map[String, Double]): Double = {
    dotProduct(x, y) / (norm(x) * norm(y))
  }
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(appName).setMaster(master)
    new SparkContext(conf)
  }

}
