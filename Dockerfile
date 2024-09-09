FROM openjdk:17-jdk-slim
COPY target/Wallet-0.0.1-SNAPSHOT.jar wallet-app.jar
ENTRYPOINT ["java", "-jar", "wallet-app.jar"]