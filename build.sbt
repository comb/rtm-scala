//BASIC PROJECT INFO
name := "rtm"

organization := "net.combinatory"

version := "1.0-SNAPSHOT"

//SCALA VERSIONS AND OPTIONS
scalaVersion := "2.9.1"

scalacOptions ++= Seq("-deprecation", "-unchecked")

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

resolvers += "Spray repo" at "http://repo.spray.cc/"

//SCALA DEPENDENCIES
libraryDependencies ++= Seq (
  "org.scalatest" %% "scalatest" % "1.7.1" % "test",
  "joda-time" % "joda-time" % "2.1",
  "org.joda" % "joda-convert" % "1.1",
  "cc.spray" %%  "spray-json" % "1.1.1"
)

//SBT BEHAVIOR
fork in Test := true

fork in Compile := true

logLevel := Level.Info //higher than Info suppresses your own printlns

traceLevel := 5
