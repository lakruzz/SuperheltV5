## To build the .jar file

```
./mvnw clean package
```

## To start the database
In this example I'm running it from docker using the community maintained image `mysql:8.0`.

It's required that you have docker installed. On Windows or Mac use docker Desktop. The database will still be accessible from port `3306` on your localhost

```
docker run \
  -e MYSQL_USER=mysql \
  -e MYSQL_PASSWORD=mysql \
  -e MYSQL_ROOT_PASSWORD=root \
  -v $(git rev-parse --show-toplevel):/repo:rw \
  --workdir /repo \
  --name mysql \
  -p 3306:3306 \
  -d mysql:8.0
```
You can not run the same command again until you have stopped and removed the container.

```
docker container rm -v -f mysql1
```

## To create and test the database

The docker container mounts the repository root.

you can then run scripts agains the database like this:

```
cd $(git rev-parse --show-toplevel)
docker exec -i mysql1 mysql -uroot -proot < src/sql/schema.sql
docker exec -i mysql1 mysql -uroot -proot < src/sql/data.sql
docker exec -i mysql1 mysql -uroot -proot < src/sql/test.sql
```



## To build the Docker image:

```
docker buildx build --platform linux/amd64 -t superhelte .
```

