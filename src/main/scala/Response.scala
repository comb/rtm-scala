/* (c) rtm-scala contributors, 2012. All rights reserved. */
package net.combinatory.rtm

import net.combinatory.rtm.Responses.ResponseBuilder
import cc.spray.json._


abstract class RtmException(msg: String, code: Option[Int])
  extends Exception(msg)
case class ServiceUnavailable(msg: String) extends RtmException(msg, Some(105))
case class InvalidApiKey(msg: String) extends RtmException(msg, Some(100))
case class InvalidAuth(msg: String) extends RtmException(msg, Some(98))
case class UnspecifiedException(msg: String, code: Option[Int])
  extends RtmException(msg, code)



/*case class Response[T](rsp:T)
object Response {
  implicit object ResponseJsonFormat extends RootJsonFormat[Response[_]] {
    def read(json: JsValue) = null
  }
}*/

object Responses {
  import DefaultJsonProtocol._ // necessary for some implicit resolutions

  type RSP[T] = Either[RtmException, T]
  type FrobType = String

  /**
   * Class responsible for extracting as Response and it's contents.
   * The contents are the domain objects.
   * @tparam T The domain object to be extracted.
   */
  class ResponseBuilder[T: JsonFormat](dataField: Option[String] = None) {
    println("Creating response builder: "+dataField)
    case class Response(rsp: RSP[T])

    object Response {

      implicit object ResponseJsonFormat extends RootJsonFormat[Response] {
        def write(obj: Response) = null

        def read(json: JsValue) = {
          //TODO handle exceptions
          val contents = json.asJsObject.getFields("rsp")(0)
          val stat = contents.asJsObject.getFields("stat")(0).convertTo[String]
          val jsonObject = dataField match {
            case Some(field) => contents.asJsObject.getFields(field)(0)
            case None => contents
          }
          println(jsonObject.prettyPrint)
          Response(
            Either.cond(stat == "ok",
              jsonObject.convertTo[T],
              //TODO take the err code or use the Exceptions classes
              new RtmException(contents.toString, None) {}
              // we also have format for exceptions
            )
          )
        }
      }
    }
  }

  trait AbstractDomainExtractor[T] {

    implicit val format: RootJsonFormat[T]
    implicit lazy val responseFormat:ResponseBuilder[T] =
      new ResponseBuilder[T]()

    def fromJson(json: JsValue): RSP[T] = {
      // TODO deal with parsing exceptions
      json.convertTo[responseFormat.Response].rsp
    }

    def fromJson(jsonString: String): RSP[T] = {
      //TODO deal with parsing exceptions
      jsonString.asJson.convertTo[responseFormat.Response].rsp
    }
  }

  case class Frob(frob: FrobType)
  object Frob extends AbstractDomainExtractor[Frob] {
    implicit override lazy val responseFormat: ResponseBuilder[Frob] =
      new ResponseBuilder[Frob]()
    implicit val format: RootJsonFormat[Frob] = jsonFormat1(Frob.apply)
  }

  case class User(id: String, username: String, fullname: String)
  object User extends AbstractDomainExtractor[User] {
    implicit val format: RootJsonFormat[User] =
      DefaultJsonProtocol.jsonFormat3(User.apply)
  }

  case class Auth(token: String, perms: String, user: User)
  object Auth extends AbstractDomainExtractor[Auth] {    println("Creating auth")
    implicit override lazy val responseFormat:ResponseBuilder[Auth] =
      new ResponseBuilder[Auth](Option("auth"))
    implicit val format: RootJsonFormat[Auth] =
      DefaultJsonProtocol.jsonFormat3(Auth.apply)
  }
}
