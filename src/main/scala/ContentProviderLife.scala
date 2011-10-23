package net.nielinjie.beautyFans

import android.net.Uri
import android.content.{ContentValues, ContentProvider}
import java.lang.UnsupportedOperationException
import android.database.Cursor

class ContentProviderMethods extends ContentProvider {
  def onCreate(): Boolean = {
    throw new UnsupportedOperationException("onCreate")
  }

  def query(p1: Uri, p2: Array[String], p3: String, p4: Array[String], p5: String): Cursor = {
    throw new UnsupportedOperationException("query")
  }

  def getType(p1: Uri): String = {
    throw new UnsupportedOperationException("getType")
  }

  def insert(p1: Uri, p2: ContentValues): Uri = {
    throw new UnsupportedOperationException("insert")
  }

  def delete(p1: Uri, p2: String, p3: Array[String]): Int = {
    throw new UnsupportedOperationException("delete")
  }

  def update(p1: Uri, p2: ContentValues, p3: String, p4: Array[String]): Int = {
    throw new UnsupportedOperationException("update")
  }
}