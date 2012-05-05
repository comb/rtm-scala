package net.combinatory.rtm
package impl

import net.combinatory.rtm.{Methods, Rtm}
import net.combinatory.rtm.Responses.{RSP, Auth, Frob}

class RtmAuthApiImpl {
  def authGetFrob(): RSP[Frob] = Frob fromJson (Rtm runMethod Methods.GetFrob)

  def authGetToken(frob: Frob): RSP[Auth] = {
    val allParams = {
      ("frob", frob.frob) :: // this feels ridiculous
        ("perms", "read") ::
        Nil
    }
    Auth fromJson (Rtm runMethod(Methods.GetToken, allParams))
  }
}
