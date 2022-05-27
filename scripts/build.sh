#!/bin/bash

echo "Building docker images"
docker build -f docker/DockerReg . --tag jared_reg
docker build -f docker/Dockerfile . --tag jared_point

echo "Network setup (this might fail if network already setup)"
docker network create jared

echo "Starting docker containers"
docker run --name point_registry -d jared_reg
docker run --name jared_point -d jared_point

echo "Connecting containers to registry"
docker network connect jared point_registry
docker network connect jared jared_point