name := "POC-HKT"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.7"

scalacOptions += "-Ypartial-unification"

libraryDependencies ++= Seq(
  "io.spray" %% "spray-json" % "1.3.4",
  "org.scalaz" %% "scalaz-core" % "7.2.27",
  "org.typelevel" %% "cats-core" % "1.5.0-RC1",
  "org.mockito" % "mockito-core" % "2.18.0" % Test,
  "org.scalatest" %% "scalatest" % "3.2.0-SNAP9" % Test
)
