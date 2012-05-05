package net.combinatory.rtm.api

/* (c) rtm-scala contributors, 2012. All rights reserved. */

import org.joda.time.DateTime


/* This is the RTM public API, restricted to read-only functions,
* wrapped in Scala types. */
trait RtmReadOnlyApi {
  def contactsGetList(): Unit

  def groupsGetList(): Unit

  def listsGetList(): Unit

  def locationsGetList(): Unit

  def reflectionGetMethods(): Unit

  def reflectionGetMethodInfo(methodName: String): Unit

  def settingsGetList(): Unit

  def tasksGetList(listId: Option[String], filter: Option[String],
                   lastSync: Option[DateTime]): Unit

  def timezonesGetList(): Unit

  def testEcho(): Unit

  def testLogin(): Unit

  def timeConvert(toTimezone: String, fromTimezone: Option[String],
                  time: Option[DateTime])

  def timeParse(text: String, timezone: Option[String],
                dateFormat: Option[String])
}
