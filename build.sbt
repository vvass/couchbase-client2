import sbt.Keys._
import sbt.Level

lazy val commonSettings = Seq(
  organization  := "44lab5",
  version       := "1.0",
  scalaVersion  := "2.11.8"
)

lazy val root = (project in file(".")).
  enablePlugins(TomcatPlugin).
  settings(commonSettings: _*).
  settings(
    name      := "couchbase-client",
    logLevel  := Level.Info
  )


resolvers ++= Seq(
  "ReactiveCouchbase Snapshots" at "https://raw.github.com/ReactiveCouchbase/repository/master/snapshots/",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "Spray Repository" at "http://repo.spray.io",
  "Spray Nightlies" at "http://nightlies.spray.io/"
)

libraryDependencies ++= {
  val iospray = "1.3.3"

  Seq(
    "org.reactivecouchbase".%%("reactivecouchbase-core")            % "0.4-SNAPSHOT",
    // -- metrics and health checks --
    "io.dropwizard.metrics".%("metrics-core")                       % "3.1.2",
    // -- package plugin --
    "javax.servlet".%("javax.servlet-api")                          % "3.0.1"         % "provided",
    // -- logging --
    "ch.qos.logback".%("logback-classic")                           % "1.1.7",
    // -- spray --
    "io.spray".%%("spray-can")                                      % iospray,
    "io.spray".%%("spray-http")                                     % iospray,
    "io.spray".%%("spray-util")                                     % iospray,
    "io.spray".%%("spray-routing")                                  % iospray,
    "io.spray".%%("spray-client")                                   % iospray,
    "io.spray".%%("spray-servlet")                                  % iospray,
    "io.spray".%%("spray-testkit")                                  % iospray         % "test",
    "io.spray".%%("spray-json")                                     % "1.3.2"
  )
}

assemblyJarName in assembly := "couchbase-client.jar"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false, includeDependency = false)

//Adds tomcat container and remote debugging
def debugTomcat = Command.command("debugTomcat") { state =>
  import com.earldouglas.xwp.ContainerPlugin.start
  val javaOpts =
    Seq(
      "-Xdebug",
      "-Xrunjdwp:server=y,transport=dt_socket,address=8080,suspend=n"
    )
  val state2 =
    Project.extract(state).append(
      Seq(javaOptions in Tomcat ++= javaOpts),
      state
    )
  Project.extract(state2).runTask(start in Tomcat, state2)
  state2
}

commands += debugTomcat
