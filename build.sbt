import sbt.Keys._

lazy val root = (project in file(".")).
  enablePlugins(JmhPlugin).
  settings(
    name := "csv-parser-benchmark",
    version := "1.0",
    scalaVersion := "2.12.4"
  )

libraryDependencies += "com.fasterxml.jackson.dataformat" % "jackson-dataformat-csv" % "2.9.2"
libraryDependencies += "org.simpleflatmapper"             % "sfm-csv"                % "3.14.1"
