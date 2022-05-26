FROM ubuntu:20.04

RUN apt-get update && apt-get -y  install openjdk-11-jdk


COPY ./src ./src
COPY ./gradle ./gradle
COPY ./gradle.* ./
COPY ./gradlew ./
COPY ./build.gradle.kts ./
COPY ./settings.gradle ./

RUN ./gradlew build && ./gradlew FatJar && mv build/libs/pointServer.jar ./ && echo "Built.  Have phun :)"

EXPOSE 8888

CMD ["java", "-jar", "pointServer.jar"]