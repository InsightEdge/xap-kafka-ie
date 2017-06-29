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

//    StreamingExamples.setStreamingLogLevels()

    val (brokers, topics) = "localhost:9092" -> "priceFeed"

    val gsConfig = InsightEdgeConfig("insightedge-space", Some("insightedge"), Some("127.0.0.1:4174"))
    val sparkConf = new SparkConf().setAppName("sample-app").setMaster("spark://127.0.0.1:7077")
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
        println("count received " + count) // displays count received 3966
        rdd.take(10).foreach(println)    // this will print all RDD such as (null,PriceFeed [id=A1^1497450614721^14, symbol=A0, price=1.0])
        rdd.values.saveToGrid()
      }
    )

    //    val messages: InputDStream[(String, String)] = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
//      ssc, kafkaParams, topicsSet

    // Start the computation
    ssc.start()
    ssc.awaitTermination()

  }

}
