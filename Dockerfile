FROM lakruzz/lamj:latest

# Set the default mysql root password
# ENV MYSQL_ROOT_PASSWORD=root

# Get the jar file from the target folder
# RUN mkdir /app || true
# COPY target/*.jar /app/
COPY src /src
COPY pom.xml /pom.xml
RUN mvn -f /pom.xml clean package


# Get the sql init files from the src/mysql/init folder
RUN mkdir /docker-entrypoint-initdb.d/ || true
COPY src/mysql/init/* /docker-entrypoint-initdb.d/




CMD set -eux; \
    lamj.init.sh; \
    java -jar /target/*.jar;


# Build like this:
# docker build  -t superhero5 .

# Run like this:
# docker run -it --rm --name superhero5 --pid=host -p 8080:8080 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root superhero5
#
#   - `docker run`: This command is used to run a container from an image.
#   - `-it`: This switch allocates a pseudo-TTY and opens an interactive terminal within the container.
#   - `--rm`: This switch removes the container automatically after it exits. (useful for development, but it resets the database every time)
#   - `--name superhero5`: This sets the name of the container to "superhero5".
#   - `--pid=host`: This runs the container in the host's PID namespace. (enable this if you  want to debug the container with a debugger or if you want to be able to stop the container with CTRL-C)
#   - `-p 8080:8080`: This maps port 8080 from the container to port 8080 on the host.
#   - `-p 3306:3306`: This maps port 3306 from the container to port 3306 on the host.
#   - `-e MYSQL_ROOT_PASSWORD=root`: This environment variable sets the root password for MySQL 'root' is default.
#   - `superhero5`: This specifies the name of the image to run.



