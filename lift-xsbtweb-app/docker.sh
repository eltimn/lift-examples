#!/bin/bash

./docker-rm.sh

set -ev

docker run -d -p 8080:8080 --name lift-xsbtweb-app default/lift-xsbtweb-app
docker logs -f lift-xsbtweb-app
