FROM amazoncorretto:17-alpine-jdk
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
VOLUME /tmp

WORKDIR /application

ADD target/*SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT  ["java", "-jar", "/application/app.jar"]