import sbt._
import Keys._

object BuildSettings {
  val buildName         = "maquereau"
  val buildOrganization = "org.mandubian"
  val buildVersion      = "0.1-SNAPSHOT"
  val buildScalaVersion = "2.11.0-SNAPSHOT"

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization := buildOrganization,
    version      := buildVersion,
    scalaVersion := buildScalaVersion
  )
}

object ApplicationBuild extends Build {

  val typesafeRepo = Seq(
    "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
    "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/"
  )

  lazy val datomic = Project(
    "maquereau", file("."),
    settings = BuildSettings.buildSettings ++ Seq(
      //logLevel := Level.Debug,
      //ivyLoggingLevel := UpdateLogging.Full,
      //scalacOptions ++= Seq("-Xlog-implicits"),
      scalacOptions := Seq("-feature", "-deprecation", "-unchecked"),
      fork in Test := true,
      //parallelExecution in Test := false,
      //javaOptions in test += "-Xmx512M -Xmx512m -Xmx1024M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=1024M",
      resolvers ++= typesafeRepo,
      libraryDependencies ++= Seq(
        "org.scala-lang" % "scala-compiler" % BuildSettings.buildScalaVersion,
        "org.scala-lang.macro-paradise" % "scala-reflect" % BuildSettings.buildScalaVersion,
        "org.specs2" % "specs2_2.10" % "1.13" % "test",
        "junit" % "junit" % "4.8" % "test"
      )
    )
  )
}
