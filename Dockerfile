FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/bank-1.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
