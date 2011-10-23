package net.nielinjie.beautyFans

import android.net.Uri


object Helpers {
  implicit def listToStringArray(list: List[String]): Array[String] =
    Array[String](list: _*)

  implicit def listToObjectArray(list: List[Object]): Array[Object] =
    Array[Object](list: _*)

  implicit def string2Uri(uri: String): Uri =
    Uri.parse(uri)

  implicit def uri2String(uri: Uri) = uri.toString
}