FROM lakruzz/lamj:latest


# RUN mkdir /app
COPY target/*.jar /app/

RUN mkdir -p /app/sql
COPY src/mysql/*.sql /app/sql/

CMD service mysql start && \
    mysql -u root -e "CREATE DATABASE IF NOT EXISTS superhero;" && \
    mysql -u root -e "SHOW DATABASES;" && \
    mysql -u root < /app/sql/schema.sql && \ 
    mysql -u root < /app/sql/data.sql && \
    mysql -u root -e "SELECT hero_name FROM superhero.superhero" && \ 
    mysql -u root -e "CREATE USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'root';" && \
    mysql -u root -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';" && \
    mysql -u root -e "SELECT host, user FROM mysql.user WHERE user='root';FLUSH PRIVILEGES;" && \ 
    java -jar /app/*.jar

#   mysql -u root -p root -e "UPDATE mysql.user SET host='%' WHERE user='root';" && \
#   mysql -u root -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';" && \


# docker build -t superhero5 .
# docker run -it --rm --name superhero5 --pid=host -p 8080:8080 -p 3306:3306 superhero5
