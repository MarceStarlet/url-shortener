name := "url-shortener"
version := "0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

val scalaTestVersion = "3.1.1"
val reactiveMongoVer = "0.16.5-play27"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play"  % scalaTestVersion % Test,
  "org.reactivemongo"      %% "play2-reactivemongo" % reactiveMongoVer
)

import play.sbt.routes.RoutesKeys

RoutesKeys.routesImport += "play.modules.reactivemongo.PathBindables._"
