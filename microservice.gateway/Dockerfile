FROM openjdk:17-alpine

WORKDIR /opt

EXPOSE 9001

COPY target/*.jar /opt/app.jar

ENTRYPOINT exec java -jar app.jar