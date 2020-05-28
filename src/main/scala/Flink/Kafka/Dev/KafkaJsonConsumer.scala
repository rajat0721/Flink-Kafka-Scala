package Flink.Kafka.Dev

import java.util.Properties

import Flink.Kafka.DataModel.Customer
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import play.api.libs.json._

object KafkaJsonConsumer {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "192.168.0.114:9092")
    properties.setProperty("group.id", "test-id")
    env.enableCheckpointing(5000) //5000 milliSec
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    //val
    val consumer = new FlinkKafkaConsumer[String]("customer", new SimpleStringSchema(), properties)
    //consumer.setStartFromEarliest()

    val stream1 = env.addSource(consumer).rebalance

    val stream2 = stream1.map( str => {
      readElement(Json.parse(str))
    })

    val stream3 = stream2.map(data => println(data.id.toInt+100+"   "+data.name))

    //stream2.uid()
    //stream1.print("YO OUT")

    env.execute("This is Kafka+Flink")

  }

  def readElement(jsonElement: JsValue): Customer = {
    val id = (jsonElement \ "id").get.toString()
    val name = (jsonElement \ "name").get.toString()
    Customer(id,name)
  }

}
