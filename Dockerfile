FROM mcr.microsoft.com/openjdk/jdk:11-ubuntu AS builder

WORKDIR /build
COPY . /build/

RUN sed -i -e 's/\r$//' /build/gradlew && /build/gradlew clean && /build/gradlew build --exclude-task test

FROM mcr.microsoft.com/openjdk/jdk:11-ubuntu

WORKDIR /build
COPY --from=builder /build/build/libs/bookmark-board-*.war .

WORKDIR /usr/src/myapp
RUN mv /build/bookmark-board-*.war /usr/src/myapp/bookmark-board.war

EXPOSE 8080

CMD ["java", "-jar", "-Dspring.profiles.active=jar", "/usr/src/myapp/bookmark-board.war"]
