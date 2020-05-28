package Flink.Kafka.Dev

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka._
//import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer.{FetcherType, OffsetStore}


//import org.apache.flink.api.scala._

object KafkaConsumer {
  def main(args: Array[String]) {
    // set up the execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "192.168.0.114:9092")
    properties.setProperty("group.id", "test-id")
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.enableCheckpointing(5000)
    /*myConsumer.setStartFromEarliest()      // start from the earliest record possible
    myConsumer.setStartFromLatest()        // start from the latest record
    myConsumer.setStartFromTimestamp(...)  // start from specified epoch timestamp (milliseconds)
    myConsumer.setStartFromGroupOffsets()  // the default behaviour*/


    val consumer = new FlinkKafkaConsumer[String]("test", new SimpleStringSchema(), properties)
    val producer = new FlinkKafkaProducer[String]("test-out",new SimpleStringSchema(), properties)

    consumer.setStartFromEarliest()

    println("making stream")
    val stream1 = env.addSource(consumer).rebalance

    stream1.print()

    //stream1.addSink(producer)

    env.execute("Flink Scala API Skeleton")
  }

  /*object KafkaStringSchema extends SerializationSchema[String, Array[Byte]] with DeserializationSchema[String] {

    import org.apache.flink.api.common.typeinfo.TypeInformation
    import org.apache.flink.api.java.typeutils.TypeExtractor

    override def serialize(t: String): Array[Byte] = t.getBytes("UTF-8")

    override def isEndOfStream(t: String): Boolean = false

    override def deserialize(bytes: Array[Byte]): String = new String(bytes, "UTF-8")

    override def getProducedType: TypeInformation[String] = TypeExtractor.getForClass(classOf[String])
  }
*/
}
