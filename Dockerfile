FROM amazoncorretto:21.0.4-alpine3.18

WORKDIR /app

COPY target/nn-bank-account-app-0.0.1-SNAPSHOT.jar /app/nn-bank-account-app-0.0.1-SNAPSHOT.jar

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "/app/nn-bank-account-app-0.0.1-SNAPSHOT.jar"]