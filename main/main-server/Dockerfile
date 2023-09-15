FROM amazoncorretto:11-alpine-jdk
COPY target/main-server-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8091