/* basic project info */
name := "json-scala-perftest"

organization := "com.micro-workflow"

version := "0.1.0-SNAPSHOT"

description := "Performance tests for Scala JSON parsing options"

homepage := Some(url("https://github.com/polymorphic/json-scala-perftest"))

startYear := Some(2012)

licenses := Seq(
  ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))
)

scmInfo := Some(
  ScmInfo(
    url("https://github.com/polymorphic/json-scala-perftest"),
    "scm:git:https://github.com/polymorphic/json-scala-perftest.git",
    Some("scm:git:git@github.com:polymorphic/json-scala-perftest.git")
  )
)

// organizationName := "My Company"

/* scala versions and options */
scalaVersion := "2.10.2"

// crossScalaVersions := Seq("2.9.1")

offline := false

scalacOptions ++= Seq("-deprecation", "-unchecked")

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

/* entry point */
mainClass in (Compile, packageBin) := Some("com.microWorkflow.jsonScalaPerftest.Main")

mainClass in (Compile, run) := Some("com.microWorkflow.jsonScalaPerftest.Main")

/* dependencies */
libraryDependencies ++= Seq (
    "com.persist" %% "persist-json" % "0.13",
    "com.fasterxml.jackson.core" % "jackson-core" % "2.2.1",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.2.1",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.2.0",
    "net.liftweb" %% "lift-json" % "2.5-M4",
    "net.minidev" % "json-smart" % "1.1.1",
    "com.rojoma" %% "rojoma-json" % "2.2.0",
    "io.spray" %%  "spray-json" % "1.2.3",
    "nl.grons" %% "metrics-scala" % "2.2.0",
    "fr.janalyse" %% "janalyse-jmx" % "0.6.1" % "compile",
    "net.sf.jopt-simple" % "jopt-simple" % "4.4",
    "play" % "play-json_2.10" % "2.2-SNAPSHOT",
     "org.jfree" % "jfreechart" % "1.0.14"
)

/* you may need these repos */
resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "spray" at "http://repo.spray.io/",
  "coda" at "http://repo.codahale.com/",
  "JAnalyse Repository" at "http://www.janalyse.fr/repository/",
  "Mandubian repository snapshots" at "https://github.com/mandubian/mandubian-mvn/raw/master/snapshots/",
  "Mandubian repository releases" at "https://github.com/mandubian/mandubian-mvn/raw/master/releases/"
)

/* sbt behavior */
logLevel in compile := Level.Warn

traceLevel := 5

/* publishing */
publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) Some(
    "snapshots" at nexus + "content/repositories/snapshots"
  )
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <developers>
    <developer>
      <id>polymorphic</id>
      <name>Dragos Manolescu</name>
      <email>coder@micro-workflow.com</email>
      <!-- <url></url> -->
    </developer>
  </developers>
)

// Josh Suereth's step-by-step guide to publishing on sonatype
// httpcom://www.scala-sbt.org/using_sonatype.html

/* assembly plugin */
mainClass in AssemblyKeys.assembly := Some("com.micro-workflow.jsonScalaPerftest.Main")

assemblySettings

test in AssemblyKeys.assembly := {}
