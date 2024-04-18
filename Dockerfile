FROM alpine:latest

RUN apk --no-cache add wget tar bash

RUN wget -O adoptopenjdk.tar.gz https://github.com/AdoptOpenJDK/openjdk21-binaries/releases/download/jdk-21%2B1/OpenJDK21U-jdk_x64_linux_hotspot_21_1.tar.gz && \
    tar zxvf adoptopenjdk.tar.gz && \
    mv jdk-21.1 /opt/adoptopenjdk && \
    rm adoptopenjdk.tar.gz

ENV JAVA_HOME /opt/adoptopenjdk

ENV PATH $PATH:$JAVA_HOME/bin

ARG PRODUCTION
ARG JDBC_DATABASE_PASSWORD
ARG JDBC_DATABASE_URL
ARG JDBC_DATABASE_USERNAME

ENV PRODUCTION ${PRODUCTION}
ENV JDBC_DATABASE_PASSWORD ${JDBC_DATABASE_PASSWORD}
ENV JDBC_DATABASE_URL ${JDBC_DATABASE_URL}
ENV JDBC_DATABASE_USERNAME ${JDBC_DATABASE_USERNAME}

WORKDIR /app
COPY ./cart-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java","-jar","cart-0.0.1-SNAPSHOT.jar"]
