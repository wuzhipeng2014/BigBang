package com.qunar.test

import org.apache.spark.{SparkConf, SparkContext}

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

    val pair = line.split("\t")
    val keyid = pair.head
    val content = pair.tail.head
    val bits = content.split(" ")

    //    val bits = line.split(" ")
    val id = bits.head
    val features = bits.tail.map(parseFeature).toMap
    (keyid, features)
  }

  // Compute dot product between to dicts
  def dotProduct(x: Map[String, Double], y: Map[String, Double]): Double = {
    var productResult = 0d
    for (elem <- x.keys) {
      productResult+=(x.apply(elem)*y.get(elem).getOrElse(0d))
    }
    productResult
  }

  // Compute norm of dict
  def norm(x: Map[String, Double]): Double = {
    var modResult = 0
    x.values.foreach(v=>modResult+=(modResult*modResult))
    math.sqrt(modResult)
  }

  // Compute cosine similarity
  def sparseCosine(x: Map[String, Double], y: Map[String, Double]): Double = {
    dotProduct(x, y) / (norm(x) * norm(y))
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("appname").setMaster("local")
    val sc = new SparkContext(conf)

    // Parse input lines
    val parsed = sc.textFile("/home/zhipengwu/secureCRT/postestinput.libsvm").map(parseLine)
    val parsedNeg = sc.textFile("/home/zhipengwu/secureCRT/negtestinput.libsvm").map(parseLine)


    val pairs = parsed.cartesian(parsedNeg).filter(x => x._1._1 != x._2._1)


    val result = pairs.collect().toList;

    println(result.toString)

    // Compute cosine similarity between pairs
    val cosineSimilarity = pairs.map { case ((k1, m1), (k2, m2)) => ((k1, k2), sparseCosine(m1, m2)) }
    val cosineResult = cosineSimilarity.collect().toList

    println(cosineResult.toString())

    //    cosineSimilarity

    //            cosineSimilarity.write.format("json").save(saveResultPath)

    println("hello")
  }

}
