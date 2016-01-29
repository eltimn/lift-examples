import sbt.Keys.{`package` => pkg}

name := "lift-xsbtweb-app"
version := "0.1-SNAPSHOT"
scalaVersion := "2.11.7"
scalacOptions := Seq("-deprecation", "-unchecked", "-feature", "-language:postfixOps", "-language:implicitConversions")

// Jetty
enablePlugins(JettyPlugin)
containerLibs in Jetty := Seq("org.eclipse.jetty" % "jetty-runner" % "9.3.7.v20160115" intransitive())
containerMain in Jetty := "org.eclipse.jetty.runner.Runner"

// Dependencies
libraryDependencies += "net.liftweb"    %% "lift-webkit"        % "3.0-SNAPSHOT"
libraryDependencies += "ch.qos.logback" %  "logback-classic"    % "1.1.3"
libraryDependencies += "javax.servlet"  %  "javax.servlet-api"  % "3.0.1" % "provided"

libraryDependencies += "org.webjars.bower" % "jquery"     % "2.2.0"
libraryDependencies += "org.webjars.bower" % "bootstrap"  % "3.3.4"
libraryDependencies += "org.webjars.bower" % "json3"      % "3.3.2"
libraryDependencies += "org.webjars.bower" % "datatables" % "1.10.10"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

// Docker
enablePlugins(DockerPlugin)
docker <<= docker.dependsOn(pkg)
dockerfile in docker := {
  val warFile = artifactPath.in(Compile, pkg).value
  new Dockerfile {
    from("jetty:9.3.6")
    copy(warFile, "/var/lib/jetty/webapps/ROOT.war")
    cmd("java", "-Djava.io.tmpdir=/tmp/jetty", "-Drun.mode=production", "-jar", "/usr/local/jetty/start.jar")
  }
}
