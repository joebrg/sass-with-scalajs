import com.typesafe.sbt.web.SbtWeb.autoImport._
import com.typesafe.sbt.web.SbtWeb
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.AutoImport._
import sbt.Keys._
import sbt._

object Build extends sbt.Build {
  lazy val root = {
    Project(id = "root", base = file("js"))
      .aggregate(jsApp)
      .enablePlugins(SbtWeb)
      .settings(
        scalaVersion := "2.11.8",
        resourceGenerators in Assets <+= Def.task[Seq[File]] {
          Seq[File]((fastOptJS in Compile in jsApp).value.data)
        }
      )
  }

  lazy val jsApp = {
    Project(id = "jsApp", base = file("jsApp"))
      .enablePlugins(ScalaJSPlugin)
      .settings(
        scalaVersion := "2.11.8"
      )
  }
}
