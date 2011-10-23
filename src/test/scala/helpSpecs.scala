package net.nielinjie.beautyFans

import android.net.Uri

import org.specs2.mutable.Specification

object ContentUriSpecs extends Specification {
  "content uri" in {
    implicit val au = Authorities("content://net.nielinjie.beauty")
    val uri = "content://net.nielinjie.beauty/sites"
    ContentUri.afterAuthorities(uri) must equalTo("/sites")

    "seg pattern" in {
      val Seg(list) = ContentUri.afterAuthorities(uri)
      list must equalTo(List("sites"))
      val uri2 = "content://net.nielinjie.beauty/site/2"
      val Seg(l) = ContentUri.afterAuthorities(uri2)
      l must equalTo(List("site", "2"))
    }

  }
}