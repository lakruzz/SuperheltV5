FROM eclipse-temurin:17-jdk-jammy
ENV profile=docker-compose
COPY src /src
COPY pom.xml /pom.xml
COPY mvnw /mvnw
COPY .mvn /.mvn
RUN set -ex; \
     ./mvnw -f /pom.xml -Dspring.profiles.active=${profile} clean package; \
     mkdir /app || true; \
     mv /target/*.jar /app/; \
     rm -rf /target; \
     rm -rf /src; \
     rm -rf /pom.xml; \
     rm -rf /mvnw; \
     rm -rf /.mvn;

EXPOSE 8080

CMD set -eux; \
    java -jar -Dspring.profiles.active=${profile} /app/*.jar;


# Build like this:
# docker build  -f Dockerfile.java -t java-app .

# Run like this:
# docker network create superhero || true
# docker run -it --rm --name java-app -p 8080:8080 --network superhero java-app