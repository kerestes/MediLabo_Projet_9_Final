FROM openjdk:17-alpine

WORKDIR /opt

EXPOSE 8082

COPY target/*.jar /opt/app.jar

ENTRYPOINT exec java -jar app.jar