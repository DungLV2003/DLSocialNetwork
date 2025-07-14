# Identity service
This microservice is responsible for:
* Onboarding users
* Roles and permissions
* Authentication

## Tech stack
* Build tool: maven >= 3.9.5
* Java: 21
* Framework: Spring boot 3.2.x
* DBMS: MySQL

## Prerequisites
* Java SDK 21
* A MySQL server

## Start application
`mvn spring-boot:run`

## Build application
`mvn clean package`

## Docker guideline
### Build docker image
`docker build -t <account>/identity-service:0.0.1 .`
### Push docker image to Docker Hub
`docker image push <account>/identity-service:0.0.1`
### Create network:
`docker network create dunglv-network`
### Start MySQL in dunglv-network
`docker run --network dunglv-network --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.36-debian`
### Run your application in dunglv-network
`docker run --name identity-service --network dunglv-network -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://<namecontainer>:3306/identity_service identity-service:0.0.1`
### Chúng ta có thể run application qua network th khi đó chúng ta truyền vào tên container cần kết nối
### nếu không dùng network thì truyền va IPAddress của container cần ket nối