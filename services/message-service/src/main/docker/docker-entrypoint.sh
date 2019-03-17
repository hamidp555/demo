#! /bin/bash

echo '======================== DEMO DOCKER CONTAINER ENTRYPOINT ==========================='

version=$1
java $JAVA_OPTS -jar message-service-$version.jar