package net.combinatory.rtm

import java.net.{ URL, URLEncoder }
import javax.net.ssl.HttpsURLConnection

import scala.io.Source

object Http {

  def runMethod(method: Method, params: List[KV]) {
    val url = this mkApiUrl (method, params)
    val body = this doGet url
    println("BODY")
    println(body)
  }

  private[this] def doGet(url: URL): String = {
    println("DO GET ON")
    println(url)
    val con = (url openConnection()).asInstanceOf[HttpsURLConnection]
    // con setConnectTimeout ?
    // con setReadTimeout ?
    con setRequestMethod "GET"
    try {
      con connect()
      Source.fromInputStream(con.getInputStream).mkString
    }
    finally {
      con disconnect()
    }
  }

  private[this] def mkApiUrl(method: Method, params: List[KV]) = {
    val allParams = {
      ("method", method.name) ::
      ("api_key", Credentials.ApiKey) ::
      ("response", "json") ::
      params
    }
    val finParams = if (method.requiresSignature) this signParams allParams
                    else allParams
    Util mkUrl (Rtm.RestBase, finParams)
  }

  private[this] def signParams(params: List[KV]): List[KV] = {
    val sortedParams = params sortWith { _._1 < _._1 }
    val sig = Util md5 (Credentials.SharedSecret + sortedParams.mkString)
    (Rtm.SigKey, sig) :: params
  }
}
