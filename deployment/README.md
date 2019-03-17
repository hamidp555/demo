## To publish to aws ECR
```bash
./gradlew publish
```

## To deploy to aws ECS 
```bash
./gradlew publish
./gradlew deployaws
```

## To only publish to aws ECR 
```bash
./gradlew publish

```
## To deploy to k8s cluster (on your local machine)

```bash
./gradlew deployk8s
./gradlew deletek8s
```

## To deploy using docker-compose (on your local machine)
```bash
./gradlew composeup
./gradlew composedwon
```



