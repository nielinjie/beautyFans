package net.nielinjie.beautyFans

import _root_.android.content._
import android.net.Uri
import android.database._
import java.lang.UnsupportedOperationException
import android.util.Log

class ContentP extends ContentProviderMethods {
  import Helpers._
  implicit val a=Authorities("content://net.nielinjie.beautyfans")
  object siteContent extends Content {
    override def query(uri: Uri, projection: Array[String], selection: String, selectionArgs: Array[String], sortOrder: String): Cursor = {
      import Helpers._
      new MatrixCursor(List("name", "url")) {
        addRow(List("mockSite", "mockUrl"))
        addRow(List("kakaSite", "uri"))
      }
    }
  }
  object documentContent extends Content {
    override def query(uri: Uri, projection: Array[String], selection: String, selectionArgs: Array[String], sortOrder: String): Cursor = {
      import Helpers._
      new MatrixCursor(List("name", "url")) {
        addRow(List("mockDocument", "mockUrl"))
        addRow(List("kakaDocument", "uri"))
      }
    }
  }

  val contentTable = Map(
    "sites" -> siteContent
  )

  override def onCreate() = {
    Log.i("Sites", "onCreate")
    true
    //super.onCreate()
  }


  override def getType(uri: Uri): String = {
    "vnd.android.cursor.dir/vnd.nielinjie.beautyFans.sites"
  }

  override def query(uri: Uri, projection: Array[String], selection: String, selectionArgs: Array[String], sortOrder: String): Cursor = {
    ContentUri.afterAuthorities(uri) match {
      case "/sites" =>
        siteContent.query(uri,projection,selection,selectionArgs,sortOrder)
      case _ => null
    }
  }
}

trait Content extends ContentProviderMethods {
  override def onCreate() = false

}