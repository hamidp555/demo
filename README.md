## Project description
```text
This appplication provides the following apis:
    - create a message:  POST /v1/messages 
    - read a message:    GET /v1/messages/<id> 
    - read all messages: GET /v1/messages 
    - update a message:  PUT /v1/messages/<id> 
    - delete a message:  DELETE /v1/messages/<id> 

```
  
## Project setup
```text
 - To configure gradle:
        create and update ~/.gradle/gradle.properties with the following values:(i.e. region=us-west-2, environment=dev)
            - environment=<env_name>
            - awsCredentialsProfile=default
            - region=<region_name>
        
 - To condfigure aws-cli: 
        create and update ~/.aws/credentials with your "aws_secret_access_key" and "aws_access_key_id" under [default] profile

 - To install ansible follow instruction at:
        https://ansible-tips-and-tricks.readthedocs.io/en/latest/ansible/install/

 - To install docker follow instructions at:
        https://docs.docker.com/install/
        
 - To install docker compose follow instructions at:
        https://docs.docker.com/compose/install/
```

## Documentations ( accessible after building the application )
```text
    - api docs: 
        http://localhost/swagger-ui.html
        
    - code coverage docs:
        file:///<project_root_path>/demo/services/message-service/build/reports/jacoco/test/html/index.html
        
    - owasp docs:
        file:///<project_root_path>/demo/services/message-service/build/reports/owasp/dependency-check-report.html
        
    - unit and component test docs:
        file:///<project_root_path>/demo/services/message-service/build/reports/tests/test/index.html
        
    - integration test docs:
        file:///<project_root_path>/demo/services/message-service/build/reports/tests/test/index.html
        
    - aws architecture diagram
         file:///<project_root_path>/demo/message-app.pdf
```

## Notes:
```text
The docker engine should be running before building the application, since the integration tests are part of the build.
Integration test step spins up messageapp and postgres docker containers.
```

### Clean and build

```bash
./gradlew clean build
```

### Deploy to using docker-compose
```bash
./gradlew composeup
./gradlew composedwon
```

### Deploy to local k8s cluster and clean up afterwards

```bash
./gradlew deployk8s
./gradlew deletek8s
```

### Publish to aws ECR
```bash
./gradlew publish
```

### To deploy to aws ECS 
### Note: pulish to ECR before deploy to aws

```bash
./gradlew deployaws
```
