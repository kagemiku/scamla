import Dependencies._

lazy val commonSettings = Seq(
  organization := "com.kagemiku",
  scalaVersion := "2.12.2",
  version      := "0.1.0-SNAPSHOT"
)

lazy val root = (project in file(".")).
  settings(
    commonSettings,
    name := "scamla",
    libraryDependencies += scalaTest % Test
  )
