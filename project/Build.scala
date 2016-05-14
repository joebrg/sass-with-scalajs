import com.typesafe.sbt.web.Import.WebKeys._
import com.typesafe.sbt.web.SbtWeb
import com.typesafe.sbt.web.SbtWeb.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import playscalajs.PlayScalaJS.autoImport._
import playscalajs.{PlayScalaJS, ScalaJSPlay}
import sbt.Keys._
import sbt.Project.projectToRef
import sbt.{PathFinder, _}

object Build extends sbt.Build {
  lazy val root =
    Project(id = "root", base = file("."))
      .aggregate(app1, app2)

  def copyDirTask(sourceDir: TaskKey[File], targetDir: SettingKey[File]) = Def.task {
    IO.copyDirectory(sourceDir.value, targetDir.value, preserveLastModified = true)
    PathFinder(sourceDir.value).***.get
  }

  /** Return a project that is the target of asset generation. Unfortunately, also needs to return
    * an internal javascript project to stop sbt from compiling that it cannot find the js project
    * on the top level.
    *
    * The project base directory needs to contain a subproject dir called "js".
    *
    * @param project Asset project skeleton
    * @param configureJsProject Opportunity to configure the javascript project.
    * @return asset project and javascript project. You should ignore the javascript project.
    */
  def jsApp(project: Project)(configureJsProject: Project => Project): (Project, Project) = {
    val jsProject =
      configureJsProject(
        Project(
          id = s"${project.id}Js", base = project.base / "js")
          .enablePlugins(ScalaJSPlugin, ScalaJSPlay)
          .dependsOn(common)
          .settings(
            emitSourceMaps := false,
            moduleName := "app"
          ))

    val assetProject =
      project
        .enablePlugins(SbtWeb, PlayScalaJS)
        .aggregate(projectToRef(jsProject))
        .settings(
          scalaVersion := "2.11.8",
          scalaJSProjects := Seq(jsProject),
          pipelineStages in Assets := Seq(scalaJSProd),
          resourceGenerators in Assets <+=
            copyDirTask(assets in Assets in common, WebKeys.public in Assets),
          persistLauncher in Compile := true,
          importDirectly := true
        )

    (assetProject, jsProject)
  }

  /** Shared javascript code */
  lazy val common =
    Project(id = "common", base = file("common"))
      .enablePlugins(ScalaJSPlugin, SbtWeb)
      .settings(
        scalaVersion := "2.11.8",
        persistLauncher in Compile := false
      )

  lazy val (app1, app1Js) = jsApp(Project(id = "app1", base = file("app1"))) {
    _.settings(
      scalaVersion := "2.11.8"
    )
  }

  lazy val (app2, app2Js) = jsApp(Project(id = "app2", base = file("app2"))) {
    _.settings(
      scalaVersion := "2.11.8"
    )
  }
}
