mvn clean install

docker network create mynetwork

docker build -t mysqlrobottaskerapi:latest -f Docker_Database/Dockerfile .

docker run --network=mynetwork --name robotTaskerApiDatabaseContainer -e MYSQL_ROOT_PASSWORD=sebastian -d -p 3306:3306 mysqlrobottaskerapi:latest

docker build -t javarobottaskerapi:latest -f Docker_Server/Dockerfile .

docker run --network=mynetwork --name robotTaskerAPIServerContainer -d -p 8080:8080 javarobottaskerapi:latest