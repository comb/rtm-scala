package net.combinatory.rtm

sealed trait Method {
  final lazy val name = "rtm." + suffix
  lazy val requiresSignature = true
  protected[this] val suffix: String
}

case object TestEcho extends Method {
  override protected[this] val suffix = "test.echo"
  override lazy val requiresSignature = false
}
// case object TestLogin extends Method {
//   override protected[this] val suffix = "test.login"
// }
// case object GroupsGetList extends Method {
//   override protected[this] val suffix = "groups.getList"
// }
// case object ListsGetList extends Method {
//   override protected[this] val suffix = "lists.getList"
// }
case object TasksGetList extends Method {
  override protected[this] val suffix = "tasks.getList"
}
