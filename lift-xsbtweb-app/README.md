Building
--------

This app requires [sbt](http://www.scala-sbt.org/). To build for the first time, run:

    bash$ sbt
    > ~jetty:start

That will start the app and automatically reload it whenever sources are modified. It will be running on http://localhost:8080

WebJars
-------

WebJars are used to retrieve the jQuery and Twitter bootstrap dependencies.

Packaging
---------

The `package` task will package the app into a WAR file. The `warPostProcess` function handles creating an SHA-1 digest of each asset file and renames them with the digest. The `Assets` snippet handles figuring out what the digest is for each file.

Docker
------

A `Dockerfile` is provided to run this app in production mode. First run `sbt package` to create the WAR file, then run `./docker.sh` to build, run, and then tail the logs. That will start a the webapp in docker in production mode. Visit http://localhost:8080 to see.

Use `./docker-rm.sh` to stop and remove the container.
