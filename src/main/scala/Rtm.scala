package net.combinatory.rtm

import org.joda.time.format.ISODateTimeFormat

object Rtm {

  def RestBase = "https://api.rememberthemilk.com/services/rest/"
  def AuthBase = "https://www.rememberthemilk.com/services/auth/"
  def TestMethod = "rtm.test.echo"

  def Encoding = "utf-8"
  def DateFmt = ISODateTimeFormat.dateTime

  def ApiKeyKey = "ee20dad216ad4f3c05d8408b0b510b80"
  def SigKey = "api_sig"
  def MethodKey = "method"
}
