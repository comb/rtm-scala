package net.combinatory.rtm

import org.joda.time.format.ISODateTimeFormat
import Methods.Method

object Rtm {

  def RestBase = "https://api.rememberthemilk.com/services/rest/"
  def AuthBase = "https://www.rememberthemilk.com/services/auth/"
  def TestMethod = "rtm.test.echo"

  def Encoding = "utf-8"
  def DateFmt = ISODateTimeFormat.dateTime

  def ApiKeyKey = "ee20dad216ad4f3c05d8408b0b510b80"
  def SigKey = "api_sig"
  def MethodKey = "method"

  object DefaultHttpParams extends HttpParams(60000, 6000)

  def runMethod(method: Method, params: List[KV] = Nil): String = {
    val url = this mkApiUrl (method, params)
    val body = new Http(DefaultHttpParams) doGet url
    println("BODY")
    println(body)
    body
  }

  private[this] def mkApiUrl(method: Method, params: List[KV] = Nil) = {
    val allParams = {
      ("method", method.name) ::
      ("api_key", Credentials.ApiKey) ::
      ("format", "json") ::
      params
    }
    val finParams = if (method.signed) this signParams allParams
                    else allParams
    Util mkUrl (Rtm.RestBase, finParams)
  }

  private[this] def signParams(params: List[KV]): List[KV] = {
    val sortedParams = params sortWith { _._1 < _._1 }
    val concatedParams = sortedParams.foldLeft("") { (acc,str) =>
      acc + str._1 + str._2
    }
    val sig = Util md5 (Credentials.SharedSecret + concatedParams)
    (Rtm.SigKey, sig) :: params
  }
}
