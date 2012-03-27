/* (c) rtm-scala contributors, 2012. All rights reserved. */
package net.combinatory.rtm

object Methods {

  type Arguments = Seq[(String, Boolean)]

  val GetFrob = SignedMethod("auth.getFrob")
  val GetToken = SignedMethod(
    "auth.getToken",
    "perms" -> false :: "frob" -> false :: Nil
  )
  val TestEcho = UnsignedMethod("test.echo")
  val TaskGetList = SignedMethod(
    "tasks.getList",
    "list_id" -> true :: "filter" -> true :: "last_sync" -> true :: Nil
  )
  val GetMethods = UnsignedMethod("reflection.getMethods")
  val GetMethodInfo = UnsignedMethod(
    "reflection.getMethodInfo",
    "method_name" -> true :: Nil
  )

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

    /* arg name and if it's optional */
    val arguments: Arguments = Nil
  }

  case class SignedMethod(
    override val suffix: String,
    override val arguments: Arguments = Nil) extends Method

  case class UnsignedMethod(
    override val suffix: String,
    override val arguments: Arguments = Nil) extends Method {
    override lazy val signed: Boolean = false
  }

  // Not sure this is useful
  case class AuthMethod(
    override val suffix: String,
    override val arguments: Arguments = Nil
  ) extends Method
}
