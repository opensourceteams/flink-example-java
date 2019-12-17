package com.opensourceteams.bigdata.flink.example.streaming

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.scala._


object WordCountRun {

  def main(args: Array[String]): Unit = {
    val hostname : String = "localhost"
    val port : Int = 9000
    val env:StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val text:DataStream[String] = env.socketTextStream(hostname,port,'\n')

    val windowCounts = text.flatMap{ w => w.split("\\s")}
                        .map{w =>(w,1)}
      .keyBy(0)
      .timeWindow(Time.milliseconds(100))
      .sum(1)
    windowCounts.print()

    env.execute("test")
  }
}
