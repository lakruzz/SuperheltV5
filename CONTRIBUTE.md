# Contribute

## Development

### To start the database
When running the database server from docker using the community maintained image `mysql:8.0`. It's required that you have docker installed. On Windows or Mac use docker Desktop. 

```shell
docker run \
  -e MYSQL_ROOT_PASSWORD=root \
  -v $(git rev-parse --show-toplevel):/repo:rw \
  --workdir /repo \
  --name mysql-server \
  -p 3306:3306 \
  -d mysql:8.0
```

The database server will then be accessible from port `3306` on your own machine. 

You can _not_ run the same command again until you have stopped and removed the container. Since both the port-forward and the container name would be conflicting.

```
docker container rm -v -f mysql-server
```
Your database server is then killed and all data is lost - make appropriate steps (e.g. dump the database before you kill the server) if necessary.

### To create and test the database

The docker run command above mounts the repository root of the host into the container as `repo` and uses the same directory as working directory.

The sql files to create the schema, inset data and test som queries are locating in the directory `src/mysql`. In the same directory you also find two `my.*.cnf` files you can use to connect to the database using the properties in there - including the password. 

You can access and run scripts against the database like this:

```
cd $(git rev-parse --show-toplevel)
container=mysql-server
mycnf=src/mysql/my.dev.cnf
docker exec -i $container mysql --defaults-file=$mycnf --database= < src/mysql/schema.sql
docker exec -i $container mysql --defaults-file=$mycnf < src/mysql/data.sql
docker exec -i $container mysql --defaults-file=$mycnf < src/mysql/test.sql
```

But note that the config files define a database too. So to run the `schema.sql` you'll need to unset the name or you will get at connection error (the database doesn't exist yet).

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

To use the production server you must build for prod and run the jar firle for prod like described above:


```shell
./mvnw -Dspring.profiles.active=prod clean package 
java -Dspring.profiles.active=prod -jar target/*.jar
```
But before you do that you set the the `spring.datasource.url` property in `src/main/resoruces/application-prod.properties` to point to a poblicly available IP on your computer  (not `localhost`, `0.0.0.0` or `127.0.0.1` these won't work in context of docker containers running in Docker Desktop).

You can use your hostname or your LAN side IP address. In the example below I use my machine name (run `hostname` in bash.) and it point to port `3307` (as opposed to `3306`).

```ini
spring.datasource.url=jdbc:mysql://Lars-Air-M2:3307/superhero
```

### Smoketest

I've added a small shell script to use for Smoke testing it takes a MySql configuration file as argument.

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