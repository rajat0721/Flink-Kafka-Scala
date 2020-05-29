ThisBuild / resolvers ++= Seq(
  "Apache Development Snapshot Repository" at "https://repository.apache.org/content/repositories/snapshots/",
  Resolver.mavenLocal
)

name := "Flink-From-Scratch"

version := "0.1"

organization := "org.example"

ThisBuild / scalaVersion := "2.11.12"

val flinkVersion = "1.10.1"

val flinkDependencies = Seq(
  "org.apache.flink" %% "flink-scala" % flinkVersion,
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion,
  "org.apache.flink" % "flink-core" % flinkVersion,
//  "org.apache.flink" %% "flink-connector-kafka" % flinkVersion,
  "org.apache.flink" %% "flink-connector-kafka-0.11" % "1.10.1",
  "org.apache.flink" % "flink-table" % "1.10.1" ,
  "com.typesafe.play" %% "play-json" % "2.6.10",
  "org.apache.flink" %% "flink-statebackend-rocksdb" % "1.0.0" % Test,
  "org.apache.flink" % "flink-contrib" % "1.10.1" pomOnly(),
  "org.apache.flink" % "flink-json" % "1.10.1",
  "com.typesafe.play" %% "play-functional" % "2.6.10",
  "org.json4s" %% "json4s-core" % "3.5.1",
  "org.json4s" %% "json4s-native" % "3.5.1",
  "org.apache.flink" %% "flink-table-api-scala-bridge" % "1.10.1",
  "org.apache.flink" %% "flink-table-planner-blink" % "1.10.1" % "provided",
  "org.apache.flink" % "flink-table-common" % "1.10.1" % "provided"

)

val otherDependencies = Seq(
  "com.typesafe" % "config" % "1.3.1"
)

lazy val root = (project in file(".")).
  settings(
    libraryDependencies ++= flinkDependencies
  )

//assembly / mainClass := Some("org.example.Job")


// make run command include the provided dependencies
Compile / run  := Defaults.runTask(Compile / fullClasspath,
  Compile / run / mainClass,
  Compile / run / runner
).evaluated

// stays inside the sbt console when we press "ctrl-c" while a Flink programme executes with "run" or "runMain"
Compile / run / fork := true
Global / cancelable := true

fork in run := true
// exclude Scala library from assembly
//assembly / assemblyOption  := (assembly / assemblyOption).value.copy(includeScala = false)
