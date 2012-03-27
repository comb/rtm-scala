package net.combinatory.rtm

import Responses._
import cc.spray.json._

object Invoke {

  /*

  /** Right represents the data from node we are interested in.
   *  Left is failure with the fail message"
   *   A sample error response:
   *     <rsp stat="fail">
   *       <err msg="Invalid frob - did you authenticate?" code="101"></err>
   *     </rsp>
   */
  def extractNode(resp: String, nodeName: String): Either[String, String] = {
    val xml = scala.xml.XML.loadString(resp)
    val stat = xml \\ "rsp" \\ "@stat" toString;
    if (stat == "ok") Right((xml \\ "rsp" \\ nodeName).text)
    //TODO add the err code
    else Left(stat +" - "+ (xml \\ "rsp" \\ "err" \\ "@msg" toString))
  }

  def getTasks(token: String) = {
    val allParams = ("auth_token", token) :: Nil
    Http runMethod (Methods.taskGetList, allParams)
  }

  */
}
