FROM amazoncorretto:21-alpine-jdk
COPY target/*.jar weather-sync.jar
ENTRYPOINT ["java","-jar","/weather-sync.jar"]