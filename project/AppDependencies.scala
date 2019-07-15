import play.sbt.PlayImport.guice
import sbt._

object AppDependencies {

  val compile = Seq(
    guice,
    "org.reactivemongo" %% "play2-reactivemongo" % "0.18.1-play27"
  )

  val test = Seq(
    "org.scalamock" %% "scalamock" % "4.3.0" % "test",
    "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % "test, it",
    "com.github.tomakehurst" % "wiremock-jre8" % "2.23.2" % "it"
  )

  val overrides: Seq[ModuleID] = {
    val jettyFromWiremockVersion = "9.2.24.v20180105"
    Seq(
      "org.eclipse.jetty" % "jetty-client" % jettyFromWiremockVersion % "it",
      "org.eclipse.jetty" % "jetty-continuation" % jettyFromWiremockVersion % "it",
      "org.eclipse.jetty" % "jetty-http" % jettyFromWiremockVersion % "it",
      "org.eclipse.jetty" % "jetty-io" % jettyFromWiremockVersion % "it",
      "org.eclipse.jetty" % "jetty-security" % jettyFromWiremockVersion % "it",
      "org.eclipse.jetty" % "jetty-server" % jettyFromWiremockVersion % "it",
      "org.eclipse.jetty" % "jetty-servlet" % jettyFromWiremockVersion % "it",
      "org.eclipse.jetty" % "jetty-servlets" % jettyFromWiremockVersion % "it",
      "org.eclipse.jetty" % "jetty-util" % jettyFromWiremockVersion % "it",
      "org.eclipse.jetty" % "jetty-webapp" % jettyFromWiremockVersion % "it",
      "org.eclipse.jetty" % "jetty-xml" % jettyFromWiremockVersion % "it",
      "org.eclipse.jetty.websocket" % "websocket-api" % jettyFromWiremockVersion % "it",
      "org.eclipse.jetty.websocket" % "websocket-client" % jettyFromWiremockVersion % "it",
      "org.eclipse.jetty.websocket" % "websocket-common" % jettyFromWiremockVersion % "it"
    )
  }
}
