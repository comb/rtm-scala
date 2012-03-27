/* (c) rtm-scala contributors, 2012. All rights reserved. */
package net.combinatory.rtm

import java.net.{ URL, URLEncoder }
import javax.net.ssl.HttpsURLConnection

import scala.io.Source

import Methods.Method

case class HttpParams(
  val readTimeoutMillis: Int,
  val connectionTimeoutMillis: Int
)

class Http(params: HttpParams) {

  // private[this] val ConnectionTimeout = 10000
  // private[this] val ReadTimeout = 100000

  def doGet(url: URL): String = {
    println("DO GET ON:" + url)
    val con = (url openConnection()).asInstanceOf[HttpsURLConnection]
    con setConnectTimeout params.connectionTimeoutMillis
    con setReadTimeout params.readTimeoutMillis
    con setRequestMethod "GET"
    val in = con.getInputStream 
    try {
      con connect()
      Source.fromInputStream(in).mkString
    }
    finally {
      in close()
      con disconnect()
    }
  }

  /*


  def mkAuthUrl(frob:String) = {
    val allParams = {
      "frob" -> frob ::
      ("perms", "read") ::
      ("api_key", Credentials.ApiKey) :: Nil
    }
    val finParams = this signParams allParams
    Util mkUrl (Rtm.AuthBase, finParams)
  }

  // to be removed
  def mkGetFrob = {
    val allParams = {
      ("method", Methods.getFrob.name) ::
      ("api_key", Credentials.ApiKey) :: Nil
    }
    val finParams = this signParams allParams
    Util mkUrl (Rtm.RestBase, finParams)
  }

  // to be removed
  def getFrob:Either[String, String] = {
    val xml=scala.xml.XML.loadString(doGet(mkGetFrob))
    val stat=(xml \\ "rsp" \\ "@stat").toString;
    if ( stat == "ok") Right((xml \\ "rsp" \\ "frob").text)
    else Left(stat)
  }

  // to be removed
  def mkGetToken(frob:String) = {
    val allParams = {
      "frob" -> frob ::
      ("method", "rtm.auth.getToken") ::
      ("perms", "read") ::
      ("api_key", Credentials.ApiKey) :: Nil
    }
    val finParams = this signParams allParams
    Util mkUrl (Rtm.RestBase, finParams)
  }
  // to me removed
  def getToken(frob:String):Either[String, String] = {
    val url = mkGetToken(frob)
    val contents = doGet(url)
    val xml = scala.xml.XML.loadString(contents);
    println("Response: "+xml)
    val stat=(xml \\ "rsp" \\ "@stat").toString;
    if ( stat == "ok") Right((xml \\ "rsp" \\ "token").text)
    else Left(stat)
  }

  */
}
