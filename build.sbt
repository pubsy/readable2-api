name := "readable2-api"

version := "1.0-SNAPSHOT"


lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "junit" % "junit" % "4.12" % "test",
  "com.googlecode.siren4j" % "siren4j" % "1.1.3",
  "mysql" % "mysql-connector-java" % "5.1.21"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

fork in run := false
