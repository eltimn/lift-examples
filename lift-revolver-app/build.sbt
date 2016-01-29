name := "lift-revolver-app"
version := "0.1-SNAPSHOT"
scalaVersion := "2.11.7"
scalacOptions := Seq("-deprecation", "-unchecked", "-feature", "-language:postfixOps", "-language:implicitConversions")

// Dependencies
libraryDependencies += "net.liftweb"       %% "lift-webkit"     % "3.0-SNAPSHOT"
libraryDependencies += "ch.qos.logback"    %  "logback-classic" % "1.1.3"
libraryDependencies += "org.eclipse.jetty" %  "jetty-server"    % "9.3.7.v20160115"
libraryDependencies += "org.eclipse.jetty" %  "jetty-webapp"    % "9.3.7.v20160115"

libraryDependencies += "org.webjars.bower" % "jquery"     % "2.2.0"
libraryDependencies += "org.webjars.bower" % "bootstrap"  % "3.3.4"
libraryDependencies += "org.webjars.bower" % "json3"      % "3.3.2"
libraryDependencies += "org.webjars.bower" % "datatables" % "1.10.10"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

Revolver.settings

mainClass in Compile := Some("code.WebApp")

// add webapp dir to classpath
resourceGenerators in Compile <+= (resourceManaged, sourceDirectory) map { (managedBase, sourceBase) =>
  val webappBase = sourceBase / "main" / "webapp"
  for {
    (from, to) <- webappBase ** "*" x rebase(webappBase, managedBase / "main" / "webapp")
  } yield {
    Sync.copy(from, to)
    to
  }
}

assemblyJarName in assembly := "lift-revolver-app.jar"

addCommandAlias("start", "reStart")
addCommandAlias("stop", "reStop")

// Docker
enablePlugins(DockerPlugin)
docker <<= (docker dependsOn assembly)
dockerfile in docker := {
  val artifact = (outputPath in assembly).value
  val artifactTargetPath = s"/${artifact.name}"
  new Dockerfile {
    from("java:openjdk-8-jre")
    copy(artifact, artifactTargetPath)
    cmd("java", "-Drun.mode=production", "-jar", artifactTargetPath)
  }
}
