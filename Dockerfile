FROM gradle:8.4.0-jdk21-alpine
WORKDIR /
ADD . .
RUN gradle build --stacktrace && /bin/mv /build/libs/balances-0.0.1-SNAPSHOT.jar /balances-service.jar

FROM openjdk:22-jdk-slim
WORKDIR /
COPY --from=0 /balances-service.jar /balances-service.jar
EXPOSE 80
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "balances-service.jar"]
