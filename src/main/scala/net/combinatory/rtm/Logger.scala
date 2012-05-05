package net.combinatory.rtm

/* (c) rtm-scala contributors, 2012. All rights reserved. */
trait Logger {
  protected[this] def logw(ref: AnyRef) = ()

  protected[this] def logd(ref: AnyRef) = ()

  protected[this] def loge(ref: AnyRef) = ()

  protected[this] def loge(err: Throwable) = ()
}
