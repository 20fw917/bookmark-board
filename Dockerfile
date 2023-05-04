FROM mcr.microsoft.com/openjdk/jdk:11-ubuntu AS builder
WORKDIR /build

COPY gradle gradlew build.gradle settings.gradle /build/
COPY gradle/wrapper /build/gradle/wrapper
COPY src /build/src
RUN ./gradlew clean && ./gradlew bootwar

FROM mcr.microsoft.com/openjdk/jdk:11-ubuntu

WORKDIR /usr/src/myapp
COPY --from=builder /build/build/libs/bookmark-board-*.war ./bookmark-board.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar"]
CMD ["bookmark-board.war"]