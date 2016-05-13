import sbt._

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.8")

addSbtPlugin("com.vmunier" % "sbt-play-scalajs" % "0.3.0")

libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.13"
addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.2.3")

// sass
resolvers += "Madoushi sbt-plugins" at "https://dl.bintray.com/madoushi/sbt-plugins/"
addSbtPlugin("org.madoushi.sbt" % "sbt-sass" % "0.9.3")