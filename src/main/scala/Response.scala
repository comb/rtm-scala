package net.combinatory.rtm

import net.combinatory.rtm.Responses.ResponseBuilder
import cc.spray.json.{DefaultJsonProtocol, JsonFormat, JsValue, RootJsonFormat}


abstract class RtmException(msg: String, code: Option[Int])
  extends Exception(msg)
case class ServiceUnavailable(msg: String) extends RtmException(msg, Some(105))
case class InvalidApiKey(msg: String) extends RtmException(msg, Some(100))
case class InvalidAuth(msg: String) extends RtmException(msg, Some(98))
case class UnspecifiedException(msg: String, code: Option[Int])
  extends RtmException(msg, code)

// {"rsp":{"stat":"ok","frob":"c99a45788cbff185e75a009fe5d378ae59272afb"}}

/*case class Response[T](rsp:T)
object Response {
  implicit object ResponseJsonFormat extends RootJsonFormat[Response[_]] {
    def read(json: JsValue) = null
  }
}*/
object Responses {
  type RSP[T] = Either[RtmException, T]
  type FrobType = String

  /**
   * Class responsible for extracting as Response and it's contents. The contents are the domain objects.
   * @tparam T The domain object to be extracted.
   */
  class ResponseBuilder[T : JsonFormat](dataField:Option[String]=None) {
    case class Response(rsp:RSP[T])
    object Response {
      implicit object ResponseJsonFormat extends RootJsonFormat[Response] {
        def write(obj: Response) = null

        def read(json: JsValue) = {
          val contents = json.asJsObject.getFields("rsp")(0) // TODO handle exceptions here
          import DefaultJsonProtocol._
          val stat = contents.asJsObject.getFields("stat")(0).convertTo[String]
          val jsonObject = dataField match {
            case Some(field) => contents.asJsObject.getFields(field)(0)
            case None => contents
          } 
          println(jsonObject.prettyPrint)
          Response(
            Either.cond(stat=="ok",
              jsonObject.convertTo[T],
              new RtmException(contents.toString,None) {} // TODO take the err code or use the Exceptions classes
            )
          )

        }
      }
    }
  }
  case class Frob(frob:FrobType)
  object Frob { // should this object extend ResponseBuilder?
    import DefaultJsonProtocol._ // TODO this entire block can be easily abstracted out
    implicit val format:RootJsonFormat[Frob] = jsonFormat1(Frob.apply)
    implicit val responseBuilder=new ResponseBuilder[Frob]()
    
    def fromJson(json:JsValue): RSP[Frob] = {
      json.convertTo[responseBuilder.Response].rsp
    }
  }

  case class Auth(token: String, perms: String)
  object Auth { // TODO this entire block can be easily abstracted out
    // should this object extend ResponseBuilder?
    import DefaultJsonProtocol._

    implicit val format: RootJsonFormat[Auth] = jsonFormat2(Auth.apply)
    implicit val responseBuilder = new ResponseBuilder[Auth](Option("auth"))

    def fromJson(json: JsValue): RSP[Auth] = {
      json.convertTo[responseBuilder.Response].rsp
    }
  }
}