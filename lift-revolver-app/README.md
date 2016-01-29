Building
--------

This app requires [sbt](http://www.scala-sbt.org/). To build for the first time, run:

    bash$ sbt
    > ~start

That will start the app and automatically reload it whenever sources are modified. It will be running on http://localhost:8081

WebJars
-------

WebJars are used to retrieve the jQuery and Twitter bootstrap dependencies.


Docker
------

A `Dockerfile` is provided to run this app in production mode. First run `sbt docker` to build the Docker image, then run `./docker.sh` to run and tail the logs. That will start a fully assembled app in docker in production mode. Visit http://localhost:8081 to see.

Use `./docker-rm.sh` to stop and remove the container.
