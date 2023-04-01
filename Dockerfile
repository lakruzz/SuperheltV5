FROM lakruzz/lamj

COPY target/*.jar /app.jar
RUN mkdir -p /docker-entrypoint-initdb.d
COPY src/sql/*.sql /docker-entrypoint-initdb.d/
CMD service mysql start && java -jar /app.jar
