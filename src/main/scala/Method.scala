package net.combinatory.rtm

sealed trait Method {
  final val name = "rtm." + suffix
  protected[this] val suffix: String
}

case object TestEcho extends Method {
  override protected[this] val suffix = "test.echo"
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
