package net.combinatory.rtm.api

trait RtmAuthApi {
  def authGetFrob(): Unit

  def authGetToken(frob: String): Unit
}
