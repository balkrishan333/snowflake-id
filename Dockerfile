FROM gradle:jdk17 as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17-oracle
EXPOSE 8080
RUN mkdir /app

ARG APPLICATION_JAR=snowflakeid.jar

COPY --from=build /home/gradle/src/build/libs/*.jar /app/$APPLICATION_JAR
ENTRYPOINT ["java", "-jar", "/app/$APPLICATION_JAR"]

