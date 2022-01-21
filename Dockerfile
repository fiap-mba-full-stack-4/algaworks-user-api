FROM openjdk:11

ARG USER_API_FILE_JAR=./build/libs/user-api-0.0.1-SNAPSHOT.jar
COPY ${USER_API_FILE_JAR} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
