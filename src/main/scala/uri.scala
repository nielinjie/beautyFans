package net.nielinjie.beautyFans

import android.net.Uri

object ContentUri {
  def afterAuthorities(uri:String)(implicit authorities:Authorities)={
    uri.ensuring {
      u=>
      u.startsWith(authorities.authorities)
    }.drop(authorities.authorities.length)
  }
}
case class Authorities(authorities:String)

object Seg{
  def unapply(uri:String):Option[List[String]]={
    uri.split("/").toList match {
      case ""::rest => Some(rest)
      case all => Some(all)
    }
  }
}
