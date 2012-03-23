package net.combinatory.rtm

object Methods {

  type Arguments = Seq[(String, Boolean)]

  val testEcho = UnsignedMethod("test.echo")
  val taskGetList = SignedMethod("tasks.getList",
    "list_id" -> true :: "filter" -> true :: "last_sync" -> true :: Nil)
  val getFrob = SignedMethod("auth.getFrob")
  val authMe = AuthMethod("") // url the user has to login to. how to deal with this?
  val getToken = SignedMethod("auth.getToken", "perms" -> false :: "frob" -> false :: Nil)

  val getMethods = UnsignedMethod("reflection.getMethods")
  val getMethodInfo = UnsignedMethod("reflection.getMethodInfo","method_name"->true :: Nil)

  object Method {
    class PimpedMethod(x: Method) {
      // pimps go here
    }
    def pimpMethod(x: Method) = new PimpedMethod(x)
  }

  sealed trait Method {
    final lazy val name = "rtm." + suffix
    lazy val signed = true
    val suffix: String

    /** arg name and if it's optional */
    val arguments: Arguments = Nil
  }

  case class SignedMethod(override val suffix: String,
                          override val arguments: Arguments = Nil) extends Method
  case class UnsignedMethod(override val suffix: String,
                            override val arguments: Arguments = Nil) extends Method {
    override lazy val signed: Boolean = false
  }

  // Not sure this is useful
  case class AuthMethod(override val suffix: String,
                        override val arguments: Arguments = Nil) extends Method


  /*case object TestEcho extends Method {
    override private[this] val suffix = "test.echo"
    override lazy val signed = false
  }*/

  // case object TestLogin extends Method {
  //   override protected[this] val suffix = "test.login"
  // }
  // case object GroupsGetList extends Method {
  //   override protected[this] val suffix = "groups.getList"
  // }
  // case object ListsGetList extends Method {
  //   override protected[this] val suffix = "lists.getList"
  // }
  /*case object TasksGetList extends Method {
    override private[this] val suffix = "tasks.getList"
    override val arguments = "list_id" -> true :: "filter" -> true :: "last_sync" -> true :: Nil
  }*/

}