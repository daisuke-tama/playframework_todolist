name := """tl"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += evolutions
libraryDependencies ++= Seq(
  jdbc,
  "com.h2database" % "h2" % "1.4.197",
  "org.playframework.anorm" %% "anorm" % "2.6.7"
)
