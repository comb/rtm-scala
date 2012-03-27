/* (c) rtm-scala contributors, 2012. All rights reserved. */
package net.combinatory.rtm

import org.joda.time.DateTime

import Responses.{ Auth, Frob, RSP }
import Requests.ApiToken

/* Implementation of the read-only api. */
class RtmReadOnlyApiImpl(token: ApiToken) {

  def authGetFrob(): RSP[Frob] = Frob fromJson (Rtm runMethod Methods.GetFrob)

  def authGetToken(frob: Frob): RSP[Auth] = {
    val allParams = {
      ("frob", frob.frob) :: // this feels ridiculous
      ("perms", "read") ::
      Nil
    }
    Auth fromJson (Rtm runMethod (Methods.GetToken, allParams))
  }

  def contactsGetList(): Unit = throw new UnsupportedOperationException
  def groupsGetList(): Unit = throw new UnsupportedOperationException
  def listsGetList(): Unit = throw new UnsupportedOperationException
  def locationsGetList(): Unit = throw new UnsupportedOperationException
  def reflectionGetMethods(): Unit = throw new UnsupportedOperationException
  def reflectionGetMethodInfo(methodName: String): Unit =
    throw new UnsupportedOperationException
  def settingsGetList(): Unit = throw new UnsupportedOperationException
  def tasksGetList(listId: Option[String], filter: Option[String],
                   lastSync: Option[DateTime]): Unit =
                     throw new UnsupportedOperationException
  def timezonesGetList(): Unit = throw new UnsupportedOperationException
  def testEcho(): Unit = throw new UnsupportedOperationException
  def testLogin(): Unit = throw new UnsupportedOperationException
  def timeConvert(toTimezone: String, fromTimezone: Option[String],
                  time: Option[DateTime]) =
    throw new UnsupportedOperationException
  def timeParse(text: String, timezone: Option[String],
                dateFormat: Option[String]) =
    throw new UnsupportedOperationException
}
