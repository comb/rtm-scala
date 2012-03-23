package net.combinatory.rtm

import org.scalatest.FunSuite

class JsonTests extends FunSuite {

  import Responses._

  val frobSample =  """{"rsp":{"stat":"ok","frob":"c99a45788cbff185e75a009fe5d378ae59272afb"}}"""
  val authSample = """
    {"rsp":{"stat":"ok","auth":{
      "token": "6d3084a6a4ede3cd16a71d4f43a4be7fbda62347",
      "perms": "read",
      "user": {
        "id": "3821339",
        "username": "pedrofurla",
        "fullname": "Pedro Furlanetto"
      }
    }}}
    """
  
  test("frob json parsing") {
    Frob.fromJson(frobSample)
  }
  test("authentication token parsing") {
    Auth.fromJson(authSample)
  }
}
