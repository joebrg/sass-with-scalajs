import com.typesafe.sbt.web.SbtWeb.autoImport._
import com.typesafe.sbt.web.SbtWeb
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.AutoImport._
import sbt.Keys._
import sbt._
import sbt.Project.projectToRef
import playscalajs.{PlayScalaJS, ScalaJSPlay}
import playscalajs.PlayScalaJS.autoImport._

object Build extends sbt.Build {
  lazy val root =
    Project(id = "root", base = file("."))
      .aggregate(app1, app2)

  lazy val common =
    Project(id = "common", base = file("common"))
      .enablePlugins(ScalaJSPlugin)
      .settings(
        scalaVersion := "2.11.8"
      )

  lazy val app1 = {
    Project(id = "app1", base = file("app1"))
      .enablePlugins(SbtWeb, PlayScalaJS)
      .aggregate(app1Js)
      .settings(
        scalaVersion := "2.11.8",
        scalaJSProjects := Seq(app1Js),
        pipelineStages := Seq(scalaJSProd)
      )
  }

  lazy val app1Js = {
    Project(id = "app1Js", base = file("app1/js"))
      .enablePlugins(ScalaJSPlugin, ScalaJSPlay)
      .dependsOn(common)
      .settings(
        scalaVersion := "2.11.8"
      )
  }

  lazy val app2 = {
    Project(id = "app2", base = file("app2"))
      .enablePlugins(SbtWeb, PlayScalaJS)
      .aggregate(app12s)
      .settings(
        scalaVersion := "2.11.8",
        scalaJSProjects := Seq(app12s),
        pipelineStages := Seq(scalaJSProd)
      )
  }

  lazy val app12s = {
    Project(id = "app2Js", base = file("app2/js"))
      .enablePlugins(ScalaJSPlugin, ScalaJSPlay)
      .dependsOn(common)
      .settings(
        scalaVersion := "2.11.8"
      )
  }
}
