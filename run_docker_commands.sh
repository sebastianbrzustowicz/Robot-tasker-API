#!/bin/bash

docker build -t mysqlrobottaskerapi:latest -f Docker_Database/Dockerfile .

docker run --name robotTaskerApiDatabaseContainer -e MYSQL_ROOT_PASSWORD=sebastian -d -p 3306:3306 mysqlrobottaskerapi:latest

docker build -t javarobottaskerapi:latest -f Docker_Server/Dockerfile .

docker run --name robotTaskerAPIServerContainer -d -p 8080:8080 javarobottaskerapi:latest