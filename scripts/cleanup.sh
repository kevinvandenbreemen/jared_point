#!/bin/bash

echo "Clearing up containers...."
docker container rm -f jared_point point_registry

echo "Cleaning up images"
docker image rm -f jared_point jared_reg