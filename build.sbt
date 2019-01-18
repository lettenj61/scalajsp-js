enablePlugins(ScalaJSPlugin)

lazy val scalaV = "2.12.8"
lazy val scalaJSV = "0.6.26"

// Project information
scalaVersion  := scalaV
organization  := "com.example"
version       := "0.1.0-SNAPSHOT"
name          := "sjs-tools-in-js"
description   := "Scala.js tools sandbox in JavaScript"

scalacOptions in Compile ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked",
  // "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Ywarn-unused-import",
  "-Ywarn-unused",
  "-P:scalajs:sjsDefinedByDefault"
)

scalaJSLinkerConfig ~= { lc =>
  lc.withSourceMap(false)
    .withModuleKind(ModuleKind.CommonJSModule)
}

scalaJSUseMainModuleInitializer := true

mainClass in (Compile, fastOptJS) := Some("com.example.cli.Main")

// Dependencies
libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-tools" % scalaJSV,
  "com.lihaoyi" %%% "utest" % "0.6.6" % "test"
)

// Configure test
testFrameworks += new TestFramework("utest.runner.Framework")
