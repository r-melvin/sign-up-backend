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
    unmanagedSourceDirectories in IntegrationTest := (baseDirectory in IntegrationTest) (base => Seq(base / "it")).value,
    libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % "it,test",
    libraryDependencies += "org.mockito" % "mockito-core" % "2.25.1" % "it,test",
    libraryDependencies += "com.github.tomakehurst" % "wiremock" % "2.23.2" % "it,test"
  )

libraryDependencies += guice
libraryDependencies += "org.reactivemongo" %% "play2-reactivemongo" % "0.16.5-play27"

val jettyVersion = "9.2.13.v20150730"
dependencyOverrides ++= Set(
  "org.eclipse.jetty" % "jetty-server" % jettyVersion,
  "org.eclipse.jetty" % "jetty-servlet" % jettyVersion,
  "org.eclipse.jetty" % "jetty-security" % jettyVersion,
  "org.eclipse.jetty" % "jetty-servlets" % jettyVersion,
  "org.eclipse.jetty" % "jetty-continuation" % jettyVersion,
  "org.eclipse.jetty" % "jetty-webapp" % jettyVersion,
  "org.eclipse.jetty" % "jetty-xml" % jettyVersion,
  "org.eclipse.jetty" % "jetty-client" % jettyVersion,
  "org.eclipse.jetty" % "jetty-http" % jettyVersion,
  "org.eclipse.jetty" % "jetty-io" % jettyVersion,
  "org.eclipse.jetty" % "jetty-util" % jettyVersion,
  "org.eclipse.jetty.websocket" % "websocket-api" % jettyVersion,
  "org.eclipse.jetty.websocket" % "websocket-common" % jettyVersion,
  "org.eclipse.jetty.websocket" % "websocket-client" % jettyVersion
)

coverageMinimum := 80

coverageHighlighting := true

coverageExcludedPackages := "<empty>;Reverse.*;router\\.*"