FROM amazoncorretto:11-alpine-jdk
COPY target/main-server-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]