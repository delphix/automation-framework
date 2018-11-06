#!/bin/bash

set -ex

DOCKERUSER=delphix
IMAGE=automation-framework

docker build -t ${DOCKERUSER}/$IMAGE:latest .
