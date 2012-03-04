package net.combinatory.rtm

import java.net.{ URL, URLEncoder }
import javax.net.ssl.HttpsURLConnection

import scala.io.Source

object Http {

  def runMethod(method: Method, params: List[(String, String)]) {
    val url = this mkUrl (method, params)
    val body = this doGet url
    println("BODY")
    println(body)
  }

  private[this] def doGet(url: URL): String = {
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

  private[this] def mkUrl(method: Method, params: List[(String, String)]) = {
    val allParams = Vector.empty ++ {
      ("method", method.name) ::
      ("api_key", Credentials.ApiKey) ::
      params
    }
    val allParamsStr = allParams match {
      case Vector() => ""
      case _   => "?" + (0 until allParams.length).map { i =>
        val (k, v) = allParams(i)
        val encV = URLEncoder encode (v, "utf-8")
        val kv = "%s=%s" format (k, encV)
        kv + { if (i < allParams.length - 1) "&" else "" }
      }.mkString
    }
    val urlStr = Urls.RestBase + allParamsStr
    new URL(urlStr)
  }
}
