#!/bin/bash

echo "Building docker images"
docker build -f docker/DockerReg . --tag jared_reg
docker build -f docker/Dockerfile . --tag jared_point

echo "Network setup (this might fail if network already setup)"
docker network create jared

echo "Starting docker containers"
docker run --name point_registry -d --network="jared" jared_reg
docker run --name jared_point -d --network="jared" jared_point
