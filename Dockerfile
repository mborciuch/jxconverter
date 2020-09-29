FROM openjdk:11

ARG JAR_FILE=jxconverterweb/target/*.war

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]