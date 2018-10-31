FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/halloweenCounter-0.0.4-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
 