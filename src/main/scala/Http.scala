package net.combinatory.rtm

import java.net.{ URL, URLEncoder }
import javax.net.ssl.HttpsURLConnection

import scala.io.Source

object Invoke {

  /** Right represents the data from node we are interested in.
   *  Left is failure with the fail message"
   *   A sample error response:
   *     <rsp stat="fail">
   *       <err msg="Invalid frob - did you authenticate?" code="101"></err>
   *     </rsp>
   */
  def extractNode(response:String,nodeName:String):Either[String, String] = {
    println("Response: " + response)
    val xml = scala.xml.XML.loadString(response)
    val stat = xml \\ "rsp" \\ "@stat" toString;
    if (stat == "ok") Right((xml \\ "rsp" \\ nodeName).text)
    else Left(stat +" - "+ (xml \\ "rsp" \\ "err" \\ "@msg" toString)) // TODO add the err code
  }
  
  def getFrob = {
    val response = Http.runMethod (Methods.getFrob,Nil)
    extractNode(response,"frob")
  }
  
  def getToken(frob:String) = {
    val allParams = {
      ("frob", frob) ::
      ("perms", "read") ::
      Nil
    }
    val response = Http.runMethod(Methods.getToken, allParams)
    extractNode(response, "token")
  }

  def getTasks(token:String) = {
    val allParams = ("auth_token", token) :: Nil
    Http.runMethod(Methods.taskGetList, allParams)
  }

}

object Http {

  import Methods.Method
  
  def runMethod(method: Method, params: List[KV]):String={
    val url = this mkApiUrl (method, params)
    val body = this doGet url
    println("BODY")
    println(body)
    body
  }

  /*private[this]*/ def doGet(url: URL): String = {
    println("DO GET ON:" + url)
    val con = (url openConnection()).asInstanceOf[HttpsURLConnection]
    // con setConnectTimeout ?
    // con setReadTimeout ?
    con setRequestMethod "GET"
    try {
      con connect()
      Source.fromInputStream(con.getInputStream).mkString // TODO does this input stream get closed?
    }
    finally {
      con disconnect()
    }
  }

  /*private[this]*/ def mkApiUrl(method: Method, params: List[KV]) = {
    val allParams = {
      ("method", method.name) ::
      ("api_key", Credentials.ApiKey) ::
      ("response", "json") ::
      params
    }
    val finParams = if (method.signed) this signParams allParams
                    else allParams
    Util mkUrl (Rtm.RestBase, finParams)
  }

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

  /*private[this]*/ def signParams(params: List[KV]): List[KV] = {
    val sortedParams = params sortWith { _._1 < _._1 }
    val concatedParams = sortedParams.foldLeft("")( (acc,str) => acc + str._1 + str._2 )
    val sig = Util md5 (Credentials.SharedSecret + concatedParams)
    (Rtm.SigKey, sig) :: params
  }
}
