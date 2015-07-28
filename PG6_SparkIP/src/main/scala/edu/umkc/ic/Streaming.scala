package edu.umkc.ic

import org.apache.spark.streaming.{StreamingContext, Duration, Seconds}
import StreamingContext._
import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream

/**
 * Created by cloudera on 7/19/15.
 */
object Streaming {
  def main(args: Array[String]): Unit = {
    if (args.length < 2) {
      System.err.println("Usage Streaming <master> <output>")
    }

    val Array(master, output) = args.take(2)

    val conf = new SparkConf().setMaster(master)
    val ssc = new StreamingContext(conf, Seconds(30))

    val lines = ssc.socketTextStream("localhost", 7777)
    val words = lines.flatMap(_.split(" "))
    val wc = words.map(x => (x, 1)).reduceByKey((x, y) => x + y)

    wc.saveAsTextFiles(output)
    wc.print

    println("pandas: sscstart")
    ssc.start()
    println("pandas: awaitTermination")
    ssc.awaitTermination()
    println("pandas: done!")
  }
}
