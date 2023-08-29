FROM amazoncorretto:11-alpine-jdk
COPY target/client-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]