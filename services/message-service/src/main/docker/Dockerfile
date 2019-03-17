FROM openjdk:8-jre-slim

ARG VERSION
ENV VERSION ${VERSION}

COPY message-service-${VERSION}.jar /opt/service/
COPY docker-entrypoint.sh /opt/service/
COPY application.properties /opt/service/

RUN chmod +x /opt/service/docker-entrypoint.sh

WORKDIR /opt/service

EXPOSE 8080

ENTRYPOINT exec ./docker-entrypoint.sh ${VERSION}


