FROM maven:latest AS builder
WORKDIR /application
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=csv-db
ENV POSTGRES_HOST=postgres
COPY . .
RUN --mount=type=cache,target=/root/.m2  mvn clean install -Dmaven.test.skip

FROM amazoncorretto:17.0.13 AS layers
WORKDIR /application
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=csv-db
ENV POSTGRES_HOST=postgres
COPY --from=builder /application/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM amazoncorretto:17.0.13
VOLUME /tmp
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=csv-db
ENV POSTGRES_HOST=postgres
RUN yum -y install shadow-utils
RUN useradd -ms /bin/bash spring-user
USER spring-user

COPY --from=layers /application/dependencies/ ./
COPY --from=layers /application/spring-boot-loader/ ./
COPY --from=layers /application/snapshot-dependencies/ ./
COPY --from=layers /application/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
