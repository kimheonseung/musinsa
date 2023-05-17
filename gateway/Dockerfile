FROM gradle:7.4-jdk11 as builder
WORKDIR /builder
COPY ./src ./src
COPY ./build.gradle ./build.gradle
COPY ./settings.gradle ./settings.gradle
RUN gradle clean build -x test

FROM openjdk:15.0.1
WORKDIR /app
COPY --from=builder /builder/build/libs/gateway-v1.jar .

EXPOSE 8080

USER nobody
ENTRYPOINT [ \
    "java", \
    "-jar", \
    "-Dspring.profiles.active=docker", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dsun.net.inetaddr.ttl=0", \
    "gateway-v1.jar" \
]