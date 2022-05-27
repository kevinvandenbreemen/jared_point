# jared_point
Experimenting with Docker

# Building

Note:  You must have docker installed!
Run all commands from the project root directory

## JARED Registry
```docker build -f docker/DockerReg . --tag jared_reg```

### JARED Point System ***
```docker build -f docker/Dockerfile . --tag jared_point```

# Network Setup
Before running your containers it is recommended you provide a network for them:

```docker network create jared```

# Starting

## JARED Registry ##

```docker run --name point_registry -d jared_reg```

## JARED Point ##

```docker run --name jared_point -d jared_point```

### Add to the Network ###

```

docker network connect jared point_registry
docker network connect jared jared_point

```