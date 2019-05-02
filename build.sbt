import sbt.Keys.libraryDependencies

name := """sign-up-backend"""
organization := "com.ben10"

routesGenerator := InjectedRoutesGenerator

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % "it,test",
    libraryDependencies += "org.mockito" % "mockito-core" % "2.25.1" % "it,test"
  )

libraryDependencies += guice
libraryDependencies += "org.reactivemongo" %% "play2-reactivemongo" % "0.16.5-play27"
