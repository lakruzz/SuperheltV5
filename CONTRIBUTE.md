## To build the .jar file

```
./mvnw clean package
```

## To build the Docker image:

```
docker buildx build --platform linux/amd64 -t superhelt .
```

