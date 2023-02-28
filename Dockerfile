# Use Amazon Corretto 11 as the base image for building the JAR
FROM amazoncorretto:11 AS builder

# Install Maven
RUN yum -y install maven

# Clone the Git repository with the source code
RUN yum -y install git
MKDIR microservices
RUN git clone https://github.com/durgasaigandreddy/user-service/tree/007-feature /microservices

RUN git checkout 007-feature

# Set the working directory to the root of the project
WORKDIR /microservices

# Build the JAR file
RUN mvn package

# Create a new, smaller container for running the app
FROM amazoncorretto:11-slim

# Copy the JAR file from the builder container to the new container
COPY --from=builder /microservices/target/user-service.jar /microservices/

# Remove Maven from the new container
RUN yum -y remove maven && yum -y clean all

# Set the command to run the Spring Boot app
CMD ["java", "-jar", "/microservices/user-service.jar"]