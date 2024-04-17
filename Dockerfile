FROM docker.io/library/eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /src/toytopiacart
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean bootjar

FROM docker.io/library/eclipse-temurin:21-jre-alpine AS runner

ARG USER_NAME=toytopiacart
ARG USER_UID=1000
ARG USER_GID=${USER_UID}

RUN addgroup -g ${USER_GID} ${USER_NAME} \
&& adduser -h /opt/toytopiacart -D -u ${USER_UID} -G ${USER_NAME} ${USER_NAME}

USER ${USER_NAME}
WORKDIR /opt/toytopiacart
COPY --from=builder --chown=${USER_UID}:${USER_GID} /src/toytopiacart/build/libs/*.jar app.jar

ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]





#FROM gradle:jdk17-alpine
#ARG PRODUCTION
#ARG JDBC_DATABASE_PASSWORD
#ARG JDBC_DATABASE_URL
#ARG JDBC_DATABASE_USERNAME
#
#ENV PRODUCTION ${PRODUCTION}
#ENV JDBC_DATABASE_PASSWORD ${JDBC_DATABASE_PASSWORD}
#ENV JDBC_DATABASE_URL ${JDBC_DATABASE_URL}
#ENV JDBC_DATABASE_USERNAME ${JDBC_DATABASE_USERNAME}
#
#WORKDIR /app
#COPY ./cart-0.0.1-SNAPSHOT.jar /app
#EXPOSE 8080
#CMD ["java","-jar","cart-0.0.1-SNAPSHOT.jar"]
