# Contributing

## Development
This application consists of both a MySql database and a Java Spring boot projket which builds to a _fat jar_ - a `.jar` file which has a built in Tomcat web server. So to develop on this code base you need:

- A place to to run your `.jar` file
- A place to host you MySql RDBMS and Database.

### Prerequsites: Docker
This note describes how you can achieve that without actually installing neither JRE, JDK or MySql server on your own machine. The following setup only requires that you can run Docker (On both MacOs and Windows: **install and start [Docker-Desktop](https://docs.docker.com/desktop/install/mac-install/)**)
 
### To start the database in mysql container
When running the database server from docker using the community maintained image `mysql:8.0`. 

```shell
docker run \
  -e MYSQL_ROOT_PASSWORD=root \
  -v $(git rev-parse --show-toplevel):/repo:rw \
  --workdir /repo \
  --name mysql-server \
  -p 3306:3306 \
  -d mysql:8.0
```

Here's a breakdown of the parameters used in the `docker run` command you provided:

- `-e MYSQL_ROOT_PASSWORD=root`: sets the environment variable `MYSQL_ROOT_PASSWORD` to `root`, which is used to set the root password for the MySQL server.
- `-v $(git rev-parse --show-toplevel):/repo:rw`: mounts the root of the current Git repository as a read-write volume at the path `/repo` inside the container. This allows the container to access and modify the contents of the repository.
- `--workdir /repo`: sets the working directory inside the container to `/repo`.
- `--name mysql-server`: assigns the name `mysql-server` to the container for easy reference.
- `-p 3306:3306`: maps port 3306 on the host machine to port 3306 inside the container. This allows applications running on the host to connect to the MySQL server running inside the container.
- `-d mysql:8.0`: runs the `mysql:8.0` image in detached mode, which means the container runs in the background and doesn't attach to the terminal. This image provides a preconfigured MySQL server with version 8.0.

To use this `docker run` command, you can simply copy and paste it into a terminal and run it (on Windows i works in teh CMD prompt, but not in PowerShell). Once the container is running, you can access the MySQL server using a MySQL client application and the credentials specified in the `MYSQL_ROOT_PASSWORD` environment variable.

Note: This docker run is designed to be reused using `docker exec`(I'll show you next). You can _not_ run the same command again until you have stopped and removed the container. Since both the port-forward (can't run) and the container name (can't assign name) would be conflicting.

To kill and remove the container, so you can re-run it:;

```
docker container rm -v -f mysql-server
```
Your database server is then killed and all data is lost - make appropriate steps (e.g. dump the database before you kill the server) if necessary.

### To create and test the database

The docker run command above mounts the repository root of the host into the container as `repo` and uses the same directory as working directory.

The sql files to create the schema, inset data and test som queries are located in the directory `src/mysql`. In the same directory you also find two `my.*.cnf` files you can use to connect to the database using the properties in there - including the password. 

You can access and run scripts against the database using the `mysql` CLI like this:

```shell
docker exec -it mysql-server mysql -uroot -p
```

Here's what the `docker exec` command obove does:

- `docker exec`: executes a command inside a running container.
- `-it`: allocates a pseudo-TTY and keeps STDIN open, which allows you to interact with the container's shell.
- `mysql-server`: specifies the name of the container you want to execute the command in.
- `mysql`: the command you want to execute inside the container, in this case, the MySQL client.
- `-uroot`: specifies the username you want to use to connect to the MySQL server. In this case, `root`.
- `-p`: prompts you for the password to use to connect to the MySQL server.

So, when you run this command in a terminal, it will open an interactive MySQL client session inside the container named `mysql-server`, using the `root` user and prompting you for the password. You can then use this session to interact with the MySQL server running inside the container, such as creating or querying databases and tables.

Once inside you can run any SQL statment, try `SHOW DATABASES;` or you can run entire scripts using the `source` command: `source src/mysql/init/1-schema.sql` or any other [mysql subcommand](https://dev.mysql.com/doc/refman/8.0/en/mysql-commands.html).

#### Running scripts directly from the terminal
You can also run the sql scripts or statements directly from the terminal, without entering the the mysql prompt.

For that you need to do a few changes:
- run the command in a way that doesn't prompt you for a password 
- don't should not use the -t switch
- Change to the root of the repo before you execute (`cd $(git rev-parse --show-toplevel`).

One example to achieve this is to create a `mysql.cnf` file and let that handle everything:

```shell
docker exec -i mysql-server mysql --defaults-file=src/mysql/my.dev.cnf --database= < src/mysql/init/1-schema.sql
```
Note that since the the first `my.dev.cnf` _also_ defines a database and the `1-schema.sql` script is the one actually creating the database I use `--database=` to override the setting and set the database to null for this particular run.

You you can also store the password in an environemtent variable and use that like this:

```shell
MYSQL_ROOT_PASSWORD=root
docker exec -it mysql-server mysql -uroot -p${MYSQL_ROOT_PASSWORD} -e "SHOW DATABASES;"
```

### To build the .jar file

The `pom.xml` defines two different profiles one for 'dev' and one for 'prod'  It allows you to use different database connection strings for different contexts.

```shell
# uses application.properties
./mvnw clean package 

# uses application-dev.properties
./mvnw -Dspring.profiles.active=dev clean package 
# ...or
 SPRING_PROFILES_ACTIVE=dev ./mvnw clean package

# uses application-prod.properties
./mvnw -Dspring.profiles.active=prod clean package 
# ...or 
 SPRING_PROFILES_ACTIVE=prod ./mvnw clean package
```
### To run the .jar file
```shell
# If application is built with application.properties
java -jar target/*.jar

# If application is built with application-dev.properties
java -Dspring.profiles.active=dev -jar target/*.jar
# ...or
SPRING_PROFILES_ACTIVE=dev java -jar target/*.jar

# If application is built with application-dprod.properties
java -Dspring.profiles.active=prod -jar target/*.jar
# ...or
SPRING_PROFILES_ACTIVE=prod java -jar target/*.jar

```

### To simulating dev and production using two different docker containers
You can simulate different environments locally by defining two different mysql servers.

Start an additional container for production like this:

```shell
docker run \
  -e MYSQL_ROOT_PASSWORD=root \
  -v $(git rev-parse --show-toplevel):/repo:rw \
  --workdir /repo \
  --name mysql-prod \
  -p 3307:3306 \
  -d mysql:8.0
```

Notice that this container has a different name and it's default port `3306` is mapped to `3307` on your own machine.

All the application.*.properties files differ only in location of the myssql server.

To use the production server you must build for prod and run the jar file for prod like described above:

```shell
./mvnw -Dspring.profiles.active=prod clean package 
java -Dspring.profiles.active=prod -jar target/*.jar
```

But before you do that you set the the `spring.datasource.url` property in `src/main/resoruces/application-prod.properties` to point to a publicly available IP on your computer  (not `localhost`, `0.0.0.0` or `127.0.0.1` these won't work in context of docker containers running in Docker Desktop).

You can use your hostname or your LAN side IP address. In the example below I use my machine name (run `hostname` in bash.) and it point to port `3307` (as opposed to `3306`).

```ini
spring.datasource.url=jdbc:mysql://Lars-Air-M2:3307/superhero
```

### Smoketest

I've added a small shell script to use for smoke testing it takes a MySql configuration file as argument.

```
Usage: smoketest.sh <defaults-file>
```

To test dev:
```shell
src/test/smoketest/smoketest.sh src/mysql/my.dev.cnf
```

To test prod:
```shell
src/test/smoketest/smoketest.sh src/mysql/my.prod.cnf
```

You can run the script without the required argument to learn more:
```shell
src/test/smoketest/smoketest.sh
```

### Building your own self-contained all-in-one docker containers

There is a Dockerfile that builds from `lakruzz/lamj` which has both MySql and Java JDK installed.

The docker file copies over the `*.jar` and the `*.sql` files, starts the database, runs the initial scripts and starts spring boot app.

To use this image:

```shell 
docker build -t superhero5 .
docker run -it --rm --name superhero5 --pid=host -p 8080:8080 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root superhero5
```

- `docker run`: This command is used to run a container from an image.
- `-it`: This switch allocates a pseudo-TTY and opens an interactive terminal within the container.
- `--rm`: This switch removes the container automatically after it exits. (useful for development, but it resets the database every time)
- `--name superhero5`: This sets the name of the container to "superhero5".
- `--pid=host`: This runs the container in the host's PID namespace. (enable this if you  want to debug the container with a debugger or if you want to be able to stop the container with CTRL-C)
- `-p 8080:8080`: This maps port 8080 from the container to port 8080 on the host.
- `-p 3306:3306`: This maps port 3306 from the container to port 3306 on the host.
- `-e MYSQL_ROOT_PASSWORD=root`: This environment variable sets the root password for MySQL 'root' is default.
- `superhero5`: This specifies the name of the image to run.

Note that `--rm` switch removes the container immediately after it's stopped. This is useful for development, but if you want the container remain simple remove this switch.

This container is specifically designed to use on docker run clouds that can automate build and deploy of single containers based on a `Dockerfile`. Try to host this repo live using [render.com](https://render.com); Simply log in with your GitHub account, point to the GitHub repo and see it build and deploy your container.

### Building your own self-contained (plural) based on community maintained images - running it all in Docker-Compose

Ideally you need seperate instances for the app and the database - In docker context that means two containers and for that purpose you would benefit from using Docker-compose.

There are two seperate Dockerfiles

- `Dockerfile.java`
- `Dockerfile.mysql`

These are also _self-contained_  but in separate _"standard"_ containers, using pure community maintained images.

They are started together using docker-compose.

You can simply run 

```
docker-compose up
```
It will build the images if they don't exist.

If you want to force a rebuild - even if images exists - before starting the containers then run:

```
docker-compose up --build
```
