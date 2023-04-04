## To start the database
When running the database server from docker using the community maintained image `mysql:8.0`. It's required that you have docker installed. On Windows or Mac use docker Desktop. 

```
docker run \
  -e MYSQL_ROOT_PASSWORD=root \
  -v $(git rev-parse --show-toplevel):/repo:rw \
  --workdir /repo \
  --name mysql-server \
  -p 3306:3306 \
  -d mysql:8.0
```

The database server will then be accessible from port `3306` on your localhost

You can _not_ run the same command again until you have stopped and removed the container.

```
docker container rm -v -f mysql-server
```
Your database server is then killed and all data is lost - make appropriate steps (e.g. dump the database before you kill the server) if necessary.

## To create and test the database

The docker run command above mounts the repository root of the host into the container as `repo` and uses the same directory as working directory.

You can access and run scripts located on your host against the database like this:

```
cd $(git rev-parse --show-toplevel)
docker exec -i mysql-server mysql -uroot -proot < src/sql/schema.sql
docker exec -i mysql-server mysql -uroot -proot < src/sql/data.sql
docker exec -i mysql-server mysql -uroot -proot < src/sql/test.sql
```

## To build the .jar file

```
./mvnw clean package
```

## To run the .jar file
```
java -jar target/*.jar
```