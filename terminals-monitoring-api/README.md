#Terminals Monitoring API

## MySQL Setup

1. Install Docker
2. Go to the root repository directory, check `docker-compose.yml` file contents
3. Run `docker-compose up -d` to start MySQL in docker
4. Run `docker ps` to see if container is running
5. Once you don't need the db anymore, run `docker-compose down`
6. If you want to stop and purge the db from data and settings completely run `docker-compose down --volumes` instead

Options to connect to the database
1. Click the Database button on the right panel, click plus, add MySQL connection
2. Run `docker exec -it terminals_mysql bash` to connect into the instance directly and then use `mysql` client

## Build

./gradlew clean build

## Run

./gradlew bootRun

## Run Demo Profile

./gradlew bootRun --args="--spring.profiles.active=demo"
