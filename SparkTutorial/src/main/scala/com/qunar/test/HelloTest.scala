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
      productResult += (x.apply(elem) * y.get(elem).getOrElse(0d))
    }
//    println(x.values.toString()+"dotproduct="+productResult)
    productResult
  }

  // Compute norm of dict
  def norm(x: Map[String, Double]): Double = {
    var modResult = 0d
    x.values.foreach(v => modResult += (v * v))
//    println(x.values.toString()+"norm="+modResult)
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
//    val parsed = sc.textFile("/home/zhipengwu/secureCRT/strictPosFeatureFile.libsvm").map(parseLine)
//    val parsedNeg = sc.textFile("/home/zhipengwu/secureCRT/strictPosFeatureFile.libsvm").map(parseLine)

    print("####开始计算cartesian")
    val pairs = parsed.cartesian(parsedNeg).filter(x => x._1._1 != x._2._1)
    print("####结束计算cartesian")



    //    val result = pairs.collect().toList;

    //    println(result.toString)

    // Compute cosine similarity between pairs
    val cosineSimilarity = pairs.map { case ((k1, m1), (k2, m2)) => ((k1, k2), sparseCosine(m1, m2)) }.filter(x=>x._2>0.1)

    print("####结束计算cosine相似性")

//    cosineSimilarity.saveAsTextFile("/home/zhipengwu/secureCRT/cosineSimilarityResult.txt")
    //    val cosineResult = cosineSimilarity.collect().toList
    val cosineResult = cosineSimilarity.take(20).toList

    println(cosineResult.toString())

    //    cosineSimilarity

    //            cosineSimilarity.write.format("json").save(saveResultPath)

    println("############程序运行结束")
  }

}
