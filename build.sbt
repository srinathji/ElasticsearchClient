name := "TestESClient"

version := "1.0"

scalaVersion := "2.12.3"

val elastic4sVersion = "0.90.2.8"

libraryDependencies ++= Seq(
  "com.sksamuel.elastic4s" %% "elastic4s-core" % "5.5.3",
  "com.sksamuel.elastic4s" %% "elastic4s-tcp" % "5.5.3",
  "com.sksamuel.elastic4s" %% "elastic4s-http" % "5.5.3",
  "com.sksamuel.elastic4s" %% "elastic4s-streams" % "5.5.3",
  "com.sksamuel.elastic4s" %% "elastic4s-circe" % "5.5.3",
  "org.apache.logging.log4j" % "log4j-api" % "2.9.1", // For Log4j2.xml file in resources
  "org.apache.logging.log4j" % "log4j-core" % "2.9.1",
  "com.sksamuel.elastic4s" %% "elastic4s-testkit" % "5.5.3" % "test"


)







/*
// libraryDependencies And a the resolver By  Cory G Watson gphat
-- tested havnig some issues with this library
libraryDependencies += "com.github.gphat" %% "wabisabi" % "2.2.0"
resolvers += "gphat" at "https://raw.github.com/gphat/mvn-repo/master/releases/"
*/
//scalaVersion := "2.12.3"

//
//// https://mvnrepository.com/artifact/org.scalastuff/esclient
//libraryDependencies += "org.scalastuff" %% "esclient" % "1.3.0"



