package Flink.Kafka.Dev

import Flink.Kafka.DataModel.WordCountModel
import org.apache.flink.api.scala._
import play.api.libs.json.JsValue

object WordCountWithDataModel {

  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

    val text = env.fromElements("rajat rajat rajat good to see you in action flink")

    val wcDataModel: DataSet[WordCountModel] = text.flatMap {
      _.toLowerCase.split("\\W+")}
      .map(WordCountModel(_,1))
        .groupBy("word")
        .sum("count")
    wcDataModel.print()
  }

  def readElement(jsonElement: JsValue): WordCountModel = {
    val word = (jsonElement \ "word").get.toString()
    val count = (jsonElement \ "count").get.toString().toInt
    WordCountModel(word,count)
  }
}
