FROM openjdk:8-jdk-alpine
MAINTAINER sonia.com
VOLUME /tmp
EXPOSE 8080
ADD target/demo.jar demo.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/.urandom","-Dspring.profiles.active=container", "-jar", "/demo.jar"]