FROM openjdk:17-alpine

WORKDIR /opt

EXPOSE 8083

RUN apk --no-cache add curl

ENV DATABASECONNECTION=jdbc:mysql://localhost:3306/medilabo_auth
ENV DATABASEUSERNAME=root
ENV DATABASEPASSWORD=rootroot

COPY target/*.jar /opt/app.jar

ENTRYPOINT exec java -jar app.jar