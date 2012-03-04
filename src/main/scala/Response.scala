package net.combinatory.rtm

abstract class RtmException(msg: String, code: Option[Int])
  extends Exception(msg)
case class ServiceUnavailable(msg: String) extends RtmException(msg, Some(105))
case class InvalidApiKey(msg: String) extends RtmException(msg, Some(100))
case class InvalidAuth(msg: String) extends RtmException(msg, Some(98))
case class UnspecifiedException(msg: String, code: Option[Int])
  extends RtmException(msg, code)
