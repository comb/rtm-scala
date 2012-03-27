package net.combinatory.rtm

trait Logger {
  protected[this] def logw(ref: AnyRef) = ()
  protected[this] def logd(ref: AnyRef) = ()
  protected[this] def loge(ref: AnyRef) = ()
  protected[this] def loge(err: Throwable) = ()
}
