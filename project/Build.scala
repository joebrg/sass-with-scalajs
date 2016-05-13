import com.typesafe.sbt.web.SbtWeb.autoImport._
import com.typesafe.sbt.web.SbtWeb
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.AutoImport._
import sbt.Keys._
import sbt._

object Build extends sbt.Build {
  lazy val root = {
    Project(id = "root", base = file("."))
      .enablePlugins(ScalaJSPlugin, SbtWeb)
      .settings(
        scalaVersion := "2.11.8",
        resourceGenerators in Assets <+= Def.task[Seq[File]] {
          Seq[File]((fastOptJS in Compile).value.data)
        }
      )
  }
}
