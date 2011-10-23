package net.nielinjie.beautyFans


import org.scalaquery.ql.basic.{BasicTable => Table}
import org.scalaquery.ql.TypeMapper._
import org.scalaquery.ql._

import android.util.Log

import org.scalaquery.session._
import org.scalaquery.session.Database.threadLocalSession
import org.scalaquery.ql.extended.SQLiteDriver.Implicit._

import android.content.Context
import android.database.sqlite.{SQLiteDatabase, SQLiteOpenHelper}

class Repository {
  val dataPath = "/data/data/net.nielinjie.beautyFans"
  val db = Database.forURL("jdbc:sqldroid:" + dataPath + "/main.sqlite", driver = "org.sqldroid.SqldroidDriver")
  db.withSession {
    try {
      SitesDB.Sites.ddl
    }
  }

  import nielinjie.util.data.Helper2._

  object Sites {
    def list(): List[String] = {
      db.withSession {
        (for (s <- Query(SitesDB.Sites)) yield s.name).list
      }
    }

  }

  object Documents {

  }

  object Images {

  }


}


object SitesDB {

  object Sites extends Table[(Int, String, String, Option[String])]("sites") {
    def id = column[Int]("id", O.NotNull)

    def name = column[String]("name")

    def url = column[String]("url")

    def description = column[Option[String]]("description")

    def * = id ~ name ~ url ~ description
  }

}
