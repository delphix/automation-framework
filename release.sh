#!/bin/bash

set -ex

DOCKERUSER=delphix
IMAGE=automation-framework

# ensure we're up to date
git pull

version=`cat VERSION`

# run build
./build.sh $version

# tag it
git add -A
git commit -m "version $version"
git tag -a "$version" -m "version $version"
git push
git push --tags
docker tag ${DOCKERUSER}/$IMAGE:latest ${DOCKERUSER}/$IMAGE:$version

# push it
docker push ${DOCKERUSER}/$IMAGE:latest
docker push ${DOCKERUSER}/$IMAGE:$version
