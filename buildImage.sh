#!/bin/bash
set -e

echo Gradle build
./gradlew build -PallWarningsAsErrors=true

imageTag=$1
if [ -z "$1" ]
  then
    echo No image tag provided. latest will be used
    imageTag=latest
fi

(exec "${BASH_SOURCE%/*}/application/buildImage.sh" $imageTag )