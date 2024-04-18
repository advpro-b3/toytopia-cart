FROM openjdk:21-slim
ARG PRODUCTION
ARG JDBC_DATABASE_PASSWORD
ARG JDBC_DATABASE_URL
ARG JDBC_DATABASE_USERNAME

# Install PostgreSQL client to download JDBC driver
RUN apt-get update && \
    apt-get install -y postgresql-client && \
    rm -rf /var/lib/apt/lists/*

ENV PRODUCTION ${PRODUCTION}
ENV JDBC_DATABASE_PASSWORD ${JDBC_DATABASE_PASSWORD}
ENV JDBC_DATABASE_URL ${JDBC_DATABASE_URL}
ENV JDBC_DATABASE_USERNAME ${JDBC_DATABASE_USERNAME}

WORKDIR /app

# Copy your application JAR file
COPY ./cart-0.0.1-SNAPSHOT.jar /app

# Add the PostgreSQL JDBC driver
RUN mkdir -p /app/lib && \
    wget -O /app/lib/postgresql.jar https://jdbc.postgresql.org/download/postgresql-{VERSION}.jar

# Expose port 8080
EXPOSE 8080

# Start the application
CMD ["java","-cp","/app/lib/*","-jar","cart-0.0.1-SNAPSHOT.jar"]
