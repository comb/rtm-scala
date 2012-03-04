package net.combinatory.rtm

import org.joda.time.DateTime

case class TaskSeries(
  val id: String,
  val created: DateTime,
  val modified: DateTime,
  val name: String,
  val source: String,
  val url: Option[String],
  val locationId: Option[String]
)

case class Task(
  val id: String,
  val due: Option[DateTime],
  val hasDueTime: Boolean,
  val added: DateTime,
  val completed: Option[DateTime],
  val deleted: Option[DateTime],
  val priority: Option[Priority],
  val wasPostponed: Boolean,
  val source: String,
  val estimate: Option[DateTime]
)

sealed trait Priority
case object High extends Priority
case object Medium extends Priority
case object Low extends Priority
