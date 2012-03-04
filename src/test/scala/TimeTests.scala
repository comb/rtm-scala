package net.combinatory.rtm

import org.scalatest.FunSuite

class TimeTests extends FunSuite {

  test("credentials are available") {
    Credentials.SharedSecret != ""
    Credentials.ApiKey != ""
  }

  test("echo") {
    Http runMethod (TestEcho, Nil)
  }
}
