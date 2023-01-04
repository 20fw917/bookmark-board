FROM mcr.microsoft.com/openjdk/jdk:11-ubuntu AS builder

WORKDIR /build
COPY . /build/

RUN sed -i -e 's/\r$//' /build/gradlew && /build/gradlew build

FROM mcr.microsoft.com/openjdk/jdk:11-ubuntu

WORKDIR /build
COPY --from=builder /build/build/libs/bookmark-board-*.jar .

WORKDIR /usr/src/myapp
RUN mv /build/bookmark-board-*.jar /usr/src/myapp/bookmark-board.jar

EXPOSE 8080

CMD ["java", "-jar", "/usr/src/myapp/bookmark-board.jar"]