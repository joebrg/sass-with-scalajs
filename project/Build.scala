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
  lazy val js = {
    Project(id = "js", base = file("js"))
      .aggregate(jsApp)
      .enablePlugins(SbtWeb, PlayScalaJS)
      .settings(
        scalaVersion := "2.11.8",
        scalaJSProjects := Seq(jsApp),
        pipelineStages := Seq(scalaJSProd)
      )
  }

  lazy val jsApp = {
    Project(id = "jsApp", base = file("jsApp"))
      .enablePlugins(ScalaJSPlugin, ScalaJSPlay)
      .settings(
        scalaVersion := "2.11.8"
      )
  }
}
