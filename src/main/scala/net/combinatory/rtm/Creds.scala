package net.combinatory.rtm

/* (c) rtm-scala contributors, 2012. All rights reserved. */

import java.io.File

import scala.io.Source

/* This is strictly for testing on a desktop JVM. */
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
