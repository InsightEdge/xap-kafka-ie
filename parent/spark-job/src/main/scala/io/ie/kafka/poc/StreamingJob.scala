package io.ie.kafka.poc

import kafka.serializer.StringDecoder
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.dstream.InputDStream
import org.insightedge.spark.context.InsightEdgeConfig
import org.common.PriceFeed
import org.insightedge.spark.implicits.all._


object StreamingJob {

  def main(args: Array[String]) {

    val (brokers, topics) = "localhost:9092" -> "priceFeed"

    val gsConfig = InsightEdgeConfig("insightedge-space", Some("insightedge"), Some("127.0.0.1:4174"))
    val sparkConf = new SparkConf().setAppName("Kafka2Grid").setMaster("spark://127.0.0.1:7077")
    gsConfig.populateSparkConf(sparkConf)
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc, Seconds(10))

    val topicsSet = topics.split(",").toSet
    val kafkaParams: Map[String, String] = Map[String, String]("metadata.broker.list" -> brokers)

    val messages: InputDStream[(String, PriceFeed)] = KafkaUtils.createDirectStream[String, PriceFeed, StringDecoder, PriceFeedStreamDecoder](
      ssc, kafkaParams, topicsSet)

    messages.foreachRDD(rdd =>
      if (!rdd.isEmpty) {
        val count = rdd.count.toInt
        println("-------------------------")
        println("count received " + count)
        rdd.take(1).foreach(println)
        rdd.values.saveToGrid()
      }
    )

    // Start the computation
    ssc.start()
    ssc.awaitTermination()

  }

}
