#!/bin/bash

./docker-rm.sh

set -ev

docker run -d -p 8081:8081 --name lift-revolver-app default/lift-revolver-app
docker logs -f lift-revolver-app
