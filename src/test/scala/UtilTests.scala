package net.combinatory.rtm

import org.scalatest.FunSuite

class UtilTests extends FunSuite {

  test("md5sum of empty string") {
    assert((impl.md5("")) === "d41d8cd98f00b204e9800998ecf8427e")
  }
}
