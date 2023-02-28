FROM amazoncorretto:11-alpine-jdk
RUN mkdir /microservices
ADD target/test-container-service-0.0.1-SNAPSHOT.jar /microservices/user-service.jar
CMD ["java","-jar","/microservices/user-service.jar"]
EXPOSE 8089