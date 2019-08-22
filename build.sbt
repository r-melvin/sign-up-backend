val appName = "sign-up-backend"

lazy val scoverageSettings = {
  import scoverage.ScoverageKeys

  Seq(
    ScoverageKeys.coverageExcludedPackages := "<empty>;Reverse.*;router\\.*",
    ScoverageKeys.coverageMinimum := 90,
    ScoverageKeys.coverageFailOnMinimum := false,
    ScoverageKeys.coverageHighlighting := true
  )
}

lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala)
  .configs(IntegrationTest)
  .settings(
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    dependencyOverrides ++= AppDependencies.overrides,
    scoverageSettings,
    PlayKeys.playDefaultPort := 9001
  )
  .settings(inConfig(IntegrationTest)(Defaults.itSettings): _*)

Keys.fork in Test := true
javaOptions in Test += "-Dlogger.resource=logback.xml"
parallelExecution in Test := true

Keys.fork in IntegrationTest := true
unmanagedSourceDirectories in IntegrationTest := (baseDirectory in IntegrationTest) (base => Seq(base / "it")).value
javaOptions in IntegrationTest += "-Dlogger.resource=logback.xml"
parallelExecution in IntegrationTest := false
