package net.combinatory.rtm

abstract class RtmException(msg: String, code: Int) extends Exception(msg)
case class ServiceUnavailable(msg: String) extends RtmException(msg, 105)
case class InvalidApiKey(msg: String) extends RtmException(msg, 100)
case class InvalidAuth(msg: String) extends RtmException(msg, 98)
case class UnspecifiedException(msg: String, code: Int)
  extends RtmException(msg, code)
