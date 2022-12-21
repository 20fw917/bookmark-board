FROM mcr.microsoft.com/openjdk/jdk:11-ubuntu AS builder
WORKDIR /build

COPY gradle gradlew build.gradle settings.gradle /build/
RUN /build/gradlew build -x test --parallel --continue > /dev/null 2>&1 || true

COPY . /build
RUN /build/gradlew build -x test --parallel

FROM mcr.microsoft.com/openjdk/jdk:11-ubuntu

COPY --from=builder /build/build/libs/bookmark-board-*.jar .
ADD ./bookmark-board-*.jar /usr/src/myapp/bookmark-board.jar

EXPOSE 8080

WORKDIR /usr/src/myapp
CMD ["java", "-jar", "./bookmark-board.jar"]