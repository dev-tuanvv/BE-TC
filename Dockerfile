#
# Build stage
#
FROM maven:latest AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:22-slim
COPY --from=build /target/tutorcenter-0.0.1-SNAPSHOT.jar tutorcenter.jar
# ENV PORT=8080
EXPOSE 8080
ENV TZ="Asia/Saigon"
ENTRYPOINT ["java","-jar","tutorcenter.jar"]