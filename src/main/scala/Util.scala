package net.combinatory.rtm

import java.math.BigInteger
import java.net.{ URL, URLEncoder }
import java.security.MessageDigest

object Util {

  def md5(str: String): String = {
    val dig = MessageDigest getInstance "MD5"
    dig update (str.getBytes, 0, str.length)
    new BigInteger(1, dig.digest).toString(16)
  }

  def mkUrl(base: String, params: List[KV]) = {
    val allParams = Vector.empty ++ params
    val allParamsStr = allParams match {
      case Vector() => ""
      case _   => "?" + (0 until allParams.length).map { i =>
        val (k, v) = allParams(i)
        val encV = URLEncoder encode (v, Rtm.Encoding)
        val kv = "%s=%s" format (k, encV)
        kv + { if (i < allParams.length - 1) "&" else "" }
      }.mkString
    }
    val urlStr = base + allParamsStr
    new URL(urlStr)
  }
}
