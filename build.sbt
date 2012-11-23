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
scalaVersion := "2.9.2"

// crossScalaVersions := Seq("2.9.1")

offline := false

scalacOptions ++= Seq("-deprecation", "-unchecked")

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

/* entry point */
mainClass in (Compile, packageBin) := Some("com.microWorkflow.jsonScalaPerftest.Main")

mainClass in (Compile, run) := Some("com.microWorkflow.jsonScalaPerftest.Main")

/* dependencies */
libraryDependencies ++= Seq (
  // "org.scalaz" %% "scalaz-core" % "7.0.0-M3",
  // "org.scalaz" %% "scalaz-effect" % "7.0.0-M3",
  // "org.scalacheck" %% "scalacheck" % "1.10.0" % "test"
)

/* you may need these repos */
resolvers ++= Seq(
  // Resolvers.sonatypeRepo("snapshots")
  // Resolvers.typesafeIvyRepo("snapshots")
  // Resolvers.typesafeIvyRepo("releases")
  // Resolvers.typesafeRepo("releases")
  // Resolvers.typesafeRepo("snapshots")
  // JavaNet2Repository,
  // JavaNet1Repository
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
