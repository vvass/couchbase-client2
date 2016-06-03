import sbt.Keys._
import sbt.Level

lazy val commonSettings = Seq(
  organization  := "44lab5",
  version       := "1.0",
  scalaVersion  := "2.11.8"
)

lazy val root = (project in file(".")).
  enablePlugins(PlayScala).
  settings(commonSettings: _*).
  settings(
    name      := "couchbase-client-scrbr",
    logLevel  := Level.Info
  )


resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= {
  val iospray = "1.3.3"

  Seq(
    "com.couchbase.client".%("java-client")                         % "2.2.7",
    "io.dropwizard.metrics".%("metrics-core")                       % "3.1.2",
    // -- package plugin --
    "javax.servlet".%("javax.servlet-api")                          % "3.0.1"         % "provided"
  )
}

assemblyJarName in assembly := "couchbase-client.jar"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false, includeDependency = false)

