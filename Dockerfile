FROM mcr.microsoft.com/openjdk/jdk:11-ubuntu AS builder
WORKDIR /build

COPY gradle gradlew build.gradle settings.gradle /build/
RUN /build/gradlew build -x test --parallel --continue > /dev/null 2>&1 || true

COPY . /build
RUN /build/gradlew build -x test --parallel

FROM mcr.microsoft.com/openjdk/jdk:11-ubuntu

WORKDIR /usr/src/myapp

COPY --from=builder /build/build/libs/bookmark-board-*.war ./bookmark-board.war

EXPOSE 8080
CMD ["java", "-jar", "./bookmark-board.war"]