# 基于官方OpenJDK 17镜像
FROM eclipse-temurin:17-jre-alpine

VOLUME /tmp
WORKDIR /app

COPY target/evms-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"] 