FROM openjdk:8-jdk-alpine
# Refer to Maven build -> finalName
# cd /opt/app
WORKDIR /opt/app
#cp target/app.jar /opt/app/app.jar
COPY target/app.jar app.jar
# java -jar /opt/app/app.jar
EXPOSE 8080
EXPOSE 80
ENTRYPOINT ["java","-jar","app.jar"]