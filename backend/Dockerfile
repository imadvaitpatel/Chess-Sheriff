FROM adoptopenjdk/openjdk15:ubi

WORKDIR /usr/app

COPY target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]