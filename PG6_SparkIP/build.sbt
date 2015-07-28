import sbt.Keys._

lazy val root = (project in file(".")).
  settings(
    name := "PG6_IPApp",
    version := "0.0.1",
    scalaVersion := "2.10.4",
    mainClass in Compile := Some("edu.umkc.ic.PG6_IPApp")
  )

exportJars := true
fork := true
assemblyJarName := "PG6_IPApp.jar"
val meta = """META.INF(.)*""".r

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs@_*) => MergeStrategy.first
  case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
  case n if n.startsWith("reference.conf") => MergeStrategy.concat
  case n if n.endsWith(".conf") => MergeStrategy.concat
  case meta(_) => MergeStrategy.discard
  case x => MergeStrategy.first
}

scalacOptions ++= Seq(
  "-optimize",
  "-unchecked",
  "-deprecation"
)

classpathTypes += "maven-plugin"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.4.0" % "provided",
  "org.apache.spark" %% "spark-streaming" % "1.4.0",
  "org.apache.spark" %% "spark-mllib" % "1.4.0",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.bytedeco" % "javacpp" % "0.11",
  "org.bytedeco" % "javacv" % "0.11",
  "org.bytedeco.javacpp-presets" % "opencv" % "2.4.11-0.11",
  "org.bytedeco.javacpp-presets" % "opencv" % "2.4.11-0.11" classifier "linux-x86",
  "org.bytedeco.javacpp-presets" % "opencv" % "2.4.11-0.11" classifier "linux-x86_64",
  "org.bytedeco.javacpp-presets" % "opencv" % "2.4.11-0.11" classifier "macosx-x86_64",
  "org.bytedeco.javacpp-presets" % "opencv" % "2.4.11-0.11" classifier "windows-x86",
  "org.bytedeco.javacpp-presets" % "opencv" % "2.4.11-0.11" classifier "windows-x86_64",
  "org.apache.spark" %% "spark-streaming-twitter" % "1.4.0",
  "org.apache.commons" % "commons-lang3" % "3.4",
  "org.eclipse.jetty" % "jetty-client" % "8.1.14.v20131031",
  "com.typesafe.play" % "play-json_2.10" % "2.4.1",
  "org.elasticsearch" % "elasticsearch-hadoop-mr" % "2.0.0.RC1",
  "net.sf.opencsv" % "opencsv" % "2.0",
  "com.github.scopt" %% "scopt" % "3.3.0",
  "org.scalaj" %% "scalaj-http" % "1.1.5",
  "com.twilio.sdk" % "twilio-java-sdk" % "3.4.1"

)

resolvers ++= Seq(
  "Akka Repository" at "http://repo.akka.io/releases/",
  "scala-tools" at "https://oss.sonatype.org/content/groups/scala-tools",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Second Typesafe repo" at "http://repo.typesafe.com/typesafe/maven-releases/",
  "JavaCV maven repo" at "http://maven2.javacv.googlecode.com/git/",
  "JavaCPP maven repo" at "http://maven2.javacpp.googlecode.com/git/",
  "JBoss Repository" at "http://repository.jboss.org/nexus/content/repositories/releases/",
  "Spray Repository" at "http://repo.spray.cc/",
  "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/",
  "Twitter4J Repository" at "http://twitter4j.org/maven2/",
  "Apache HBase" at "https://repository.apache.org/content/repositories/releases",
  "Twitter Maven Repo" at "http://maven.twttr.com/",
  "Mesosphere Public Repository" at "http://downloads.mesosphere.io/maven",
  Resolver.sonatypeRepo("public")
)

//javacOptions in Compile ++= Seq("-source", "1.7", "-target", "1.7")