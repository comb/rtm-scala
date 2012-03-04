package net.combinatory.rtm

import java.io.File

import scala.io.Source

object Credentials {

  lazy val HomeDir = new File(
    System getProperty "user.home", ".credentials/rtm"
  )

  lazy val SharedSecret = Source.fromFile(
    new File(HomeDir, "shared_secret")
  ).mkString.trim

  lazy val ApiKey = Source.fromFile(new File(HomeDir, "api_key")).mkString.trim

  lazy val Frob = Source.fromFile(new File(HomeDir, "frob")).mkString.trim
}