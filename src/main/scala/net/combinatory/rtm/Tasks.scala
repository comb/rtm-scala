/* (c) rtm-scala contributors, 2012. All rights reserved. */
package net.combinatory.rtm

import org.joda.time.DateTime

case class TaskSeries(
  id: String,
  created: DateTime,
  modified: DateTime,
  name: String,
  source: String,
  url: Option[String],
  locationId: Option[String]
)

case class Task(
  id: String,
  due: Option[DateTime],
  hasDueTime: Boolean,
  added: DateTime,
  completed: Option[DateTime],
  deleted: Option[DateTime],
  priority: Option[Priority],
  wasPostponed: Boolean,
  source: String,
  estimate: Option[DateTime]
)

sealed trait Priority
case object High extends Priority
case object Medium extends Priority
case object Low extends Priority
