FROM openjdk:17-alpine

WORKDIR /opt

EXPOSE 8083

COPY target/*.jar /opt/app.jar

ENTRYPOINT exec java -jar app.jar