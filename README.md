## Robot Tasker API

Robot Tasker API is a Java-based, server-side application created to establish fast and real-time communication with robots 'over the internet'.   
It is simple connector between the client and the vehicle with no redundant restrictions on the data transmitted.    
Sending desired control values or (less likely) tasks to robot is crucial in terms of connection speed.   
Therefore 'over the internet' solutions are not so popular, as opposed to short-range methods.       
In this case, the WebSocket method was used, which provides high-speed data transfer. 
Moreover, this solution adds a teleoperational aspect to the robot control.  
A multi-channel approach was used to implement the WebSocket, allowing server to handle multiple connections.  
The application uses a MySQL database to store user and vehicle information.  
### Disclaimer
Complete version which guarantee best performance is not available publicly.   
Only alpha version of API with license restrictions is available.   
Please contact me if you are interested in cooperation.   
I am willing to help.  

## Deploy

All in one deploy copy-paste commands for windows and linux systems.

Windows:
```powershell
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
.\run_docker_commands.ps1
```
Linux:
```console
chmod +x run_docker_commands.sh
./run_docker_commands.sh
```

## Docker

Follow these commands if u want to make containers manually and adjust them to your prorities.

### Database

The Dockerfile will provide containerisation and initialisation of the MySQL database.  
There is a set of commands to get to the same point when starting the application.  
Firstly make sure you are in main project directory.   
Now u can execute building process:   
```console
docker build -t mysqlrobottaskerapi:latest -f Docker_Database/Dockerfile .
```
You can then run the container with the default password or you can change it (don't forget to change it in the project properties too):
```console
docker run --name robotTaskerApiDatabaseContainer -e MYSQL_ROOT_PASSWORD=sebastian -d -p 3306:3306 mysqlrobottaskerapi:latest
```
Now the MySQL container should run properly.

### Server

Main Dockerfile will provide containerisation and initialisation of the Java application database.  
There is a set of commands to get to the same point when starting the application.  
Make sure you are in main project directory.   
Now u can execute building process:   
```console
docker build -t javarobottaskerapi:latest -f Docker_Server/Dockerfile .
```
I chose port 8081 for Java container, it is up to you.    
You can then run the container:
```console
docker run --name robotTaskerAPIServerContainer -d -p 8080:8080 javarobottaskerapi:latest
```
Now the Java container should run properly.

## Initialize development stage

If u want to make a changes to API u need to compile and run API created in Java.
Make sure you are in main project directory.   
Firstly install all dependencies:
```console
mvn clean install
```
Then run application:
```console
mvn spring-boot:run
```
Now everything should be set up.

## Endpoints

Two architectures were used to create the API: Rest API and WebSocket.

### Rest API

Rest API part embraces user and vehicle management issues.    
Some of the options implemented are creating account, changing user's data, vehicle registration.
Rest endpoints for client only:    

| HTTP method | endpoint | description | request type | response type |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| :yellow_circle: POST | /rest/user/register | register new user | User | int |
| :red_circle: DELETE | /rest/user/delete/{userId} | delete user | String | int |
| :yellow_circle: POST | /rest/user/login | login user | &lt;String, String&gt; | String |
| :purple_circle: PATCH | /rest/user/changedata | change user's data | User | String |
| :large_blue_circle: PUT | /rest/admin/changedata | user data management | User | String |
| :yellow_circle: POST | /rest/vehicle/register | register user's vehicle | &lt;String, String&gt; | String |
| :yellow_circle: POST | /rest/vehicle/custom/register | register custom vehicle | Vehicle | String |
| :green_circle: GET | /rest/vehicle/information | information about user's vehicles | String | List&lt;Vehicle&gt; |
| :yellow_circle: POST | /rest/vehicle/delete | deregistration user's vehicle | String | String |

### WebSocket

A websocket approach was implemented for fast, real-time data transmission between the client and the vehicle.   
Websocket is implemented as multi-channel server-side application with STOMP approach.    
An example implementation of the publisher and subscriber can be found in the `WebSocket_manual_test` folder.    
One assumption made is that the vehicle should have its permanent vehicleId, which is similar to the MAC.    
The vehicleId should be registered for recognition and that is all.
WebSocket endpoints for client (publisher) and vehicle (subscriber):      

| STOMP method | endpoint | description | request type | response type |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| :yellow_circle: CONNECT | /app/vehicle/connect | create new channel | String | null |
| :red_circle: DISCONNECT  | /app/vehicle/disconnect | delete channel | String | null |
| :yellow_circle: CONNECT | /websocket-endpoint | connect vehicle to ws | null | null |
| :red_circle: DISCONNECT  | not implemented | disconnect vehicle from ws | null | null |
| :green_circle: SUBSCRIBE  | /topic/vehicle-status/{vehicleId} | connection status | null | String |
| :green_circle: SUBSCRIBE  | /topic/vehicle-data/{vehicleId} | get actual data | null | String |
| :large_blue_circle: SEND  | /app/vehicle/connect | connect to specific vehicle | String | null |
| :large_blue_circle: SEND  | /app/vehicle/disconnect | disconnect from specific vehicle | String | null |
| :large_blue_circle: SEND  | /app/vehicle/data | send actual data to vehicle | String | null |

Implemented WebSocket for specific frame formats outlined in the 'Data transmitted to vehicle' section.      
This WebSocket is single channel version for local use.       
It is helpful for development, debugging and testing new features.      

| STOMP method | endpoint | description | request type | response type |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| :large_blue_circle: SEND | /websocket-single-room | local WebSocket channel | String | String |

## MySQL database

A MySQL database was used to store user and vehicle information.    
The entire database is containerised using Docker.    
The JDBC interface has been used to create a connection to the database.    

### Table: robotTaskerApi.users

| Column Name   | Data Type             | Constraints     |
|---------------|-----------------------|-----------------|
| userId        | VARCHAR(36)           | PRIMARY KEY     |
| login         | VARCHAR(255) NOT NULL |                 |
| password      | VARCHAR(255) NOT NULL |                 |
| email         | VARCHAR(255) NOT NULL |                 |
| phoneNum      | INT NOT NULL          |                 |
| role          | VARCHAR(50) NOT NULL  |                 |
| accCreated    | DATETIME NOT NULL     |                 |

### Table: robotTaskerApi.vehicles

| Column Name   | Data Type             | Constraints     |
|---------------|-----------------------|-----------------|
| vehicleId     | VARCHAR(255) NOT NULL | PRIMARY KEY     |
| userID        | VARCHAR(36) NULL      | FOREIGN KEY (userId) REFERENCES robotTaskerApi.users(userId) |
| vehicleName   | VARCHAR(255) NOT NULL |                 |
| vehicleType   | VARCHAR(50) NOT NULL  |                 |
| registrationTime | DATETIME NULL      |                 |

## Data transmitted to vehicle

The data transferred have to be the same type in client and vehicle.    
Handshake should be established between server and client according to data order.    
The software provides auto-response after receiving message.      
Keywords and types may vary depending on vehicle type used.     
Current state of object is fitting quadcopter data.     
The data is sent and received in raw string format and its values stands for variables below.      

From client:

```
VEHICLE                                 // <- fixed prefix for vehicle message
0                                       // <- actual roll from sensor
0                                       // <- actual pitch from sensor
0                                       // <- actual yaw from sensor
0                                       // <- actual altitude from sensor
0                                       // <- actual isClamp
END                                     // <- fixed ending statement of message
```

From vehicle:

```
CLIENT                                  // <- fixed prefix for client message
4436ed9a-5228-46c0-b825-6d0a3cd90437    // <- vehicleId
1                                       // <- mode
0                                       // <- vtol
0                                       // <- x
0                                       // <- y
0                                       // <- alt
0                                       // <- yaw
false                                   // <- camTrig
false                                   // <- camTog
0                                       // <- camPitch
false                                   // <- clamp
END                                     // <- fixed ending statement of message
```

## Tests

Some simple JUnit tests have been implemented:
```java
testRegisterUser_SuccessfulRegistration()
testRegisterUser_FailedRegistration()
testLoginUser_SuccessfulLogin()
testLoginUser_FailedLogin()
testRegisterVehicle_SuccessfulRegistration()
testRegisterVehicle_FailedRegistration()
testDeleteVehicle_SuccessfulDeletion()
testDeleteVehicle_FailedDeletion()
testChangeUserData_SuccessfulChange()
testChangeUserData_FailedChange()
testChangeUserDataByAdmin_SuccessfulChange()
testChangeUserDataByAdmin_FailedChange()
testRegisterCustomVehicle_SuccessfulRegister()
testRegisterCustomVehicle_FailedRegister()
```

## License

Robot Tasker API is released under the CC BY-NC-ND 4.0 license.

## Author

Sebastian Brzustowicz &lt;Se.Brzustowicz@gmail.com&gt;

