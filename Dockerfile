FROM eclipse-temurin:21-jdk AS builder

WORKDIR /workspace

COPY gradlew build.gradle settings.gradle gradle.properties ./
COPY gradle gradle
COPY src src

RUN chmod +x gradlew
RUN ./gradlew installDist --no-daemon

FROM eclipse-temurin:21-jre

WORKDIR /opt/java-janggi

COPY --from=builder /workspace/build/install/java-janggi /opt/java-janggi

ENTRYPOINT ["/opt/java-janggi/bin/java-janggi"]
