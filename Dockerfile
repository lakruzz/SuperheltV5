FROM lakruzz/lamj
# https://hub.docker.com/_/eclipse-temurin

# RUN mkdir /opt/app
# COPY target/*.jar /opt/app

# RUN mkdir /app
COPY target/*.jar /app/

RUN mkdir -p /app/sql
COPY src/mysql/*.sql /app/sql/


# RUN mkdir -p /docker-entrypoint-initdb.d
# COPY src/mysql/*.sql /docker-entrypoint-initdb.d/
CMD service mysql start && \
    mysql -u root -e "CREATE DATABASE IF NOT EXISTS superhero;" && \
    mysql -u root -e "SHOW DATABASES;" && \
    mysql -u root < /app/sql/schema.sql && \ 
    mysql -u root < /app/sql/data.sql && \
    mysql -u root -e "SELECT hero_name FROM superhero.superhero" && \ 
    mysql -u root -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';FLUSH PRIVILEGES;" && \
    java -jar /app/*.jar


# docker build -t superhero5 .
# docker run -it --rm --name superhero5 --pid=host -p 8080:8080 -p 3306:3306 superhero5
