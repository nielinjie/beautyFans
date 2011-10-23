import sbt._

import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq(
    name := "beauty fans",
    version := "0.1",
    scalaVersion := "2.9.0-1",
    platformName in Android := "android-14"
  )

  lazy val fullAndroidSettings =
    General.settings ++
      AndroidProject.androidSettings ++
      TypedResources.settings ++
      AndroidMarketPublish.settings ++ Seq(
      keyalias in Android := "change-me",
      libraryDependencies += "org.scalatest" %% "scalatest" % "1.6.1" % "test",
      libraryDependencies += "org.specs2" %% "specs2" % "latest.release" % "test",
      libraryDependencies += "nielinjie" %% "util.data" % "latest.release",
      libraryDependencies += "org.scalaquery" %% "scalaquery" % "0.9.5"
    )
}

object AndroidBuild extends Build {
  val toKeep = List(
    "scala.Function1",
    "scala.Function2",
    "scala.Tuple2",
    "scala.collection.immutable.List",
    "scala.collection.Seq",
    "org.scalaquery**",
    "org.sqldroid**",
    "org.xml.sax.EntityResolver",
    "nielinjie.util.data.Helper*",
    "nielinjie.util.data.Helper2*"

    //...more class names here...
  )

  val keepOptions = toKeep.map {
    "-keep public class " + _
  }


  def proguardOptions = (keepOptions /*++ dontNoteOptions*/) mkString " "

  lazy val main = Project(
    "beauty fans",
    file("."),
    settings = General.fullAndroidSettings
      ++ Seq(
      proguardOption in Android ~= {
        oldvalue: String =>
          proguardOptions + " -dontobfuscate " + " -printusage usage.txt"

      }
    )

  )

  lazy val tests = Project(
    "tests",
    file("tests"),
    settings = General.settings ++ AndroidTest.androidSettings
  ) dependsOn main
}
