#!/bin/bash

set -ex

#set DOCKERUSER as Environment Variable
#export DOCKERUSER="dockeruser"
IMAGE=daf

docker build -t ${DOCKERUSER}/$IMAGE:latest .
