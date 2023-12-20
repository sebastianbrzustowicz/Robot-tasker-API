## Initialize app

Initializing app is possible with this command:
```
mvn spring-boot:run
```

## Endpoints

Rest API part embraces user and vehicle management issues.    
Some of the options implemented are creating account, changing user's data, vehicle registration.
Rest endpoints for client only:    

| HTTP method | endpoint | description | request type | response type |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| :yellow_circle: POST | /rest/user/register | register new user | User | int |
| :red_circle: DELETE | /rest/user/delete/{userId} | delete user | String | int |
| :yellow_circle: POST | /rest/user/login | login user | &lt;String, String&gt; | String |
| :purple_circle: PATCH | /rest/user/changedata | change user's data | User | String |
| :yellow_circle: POST | /rest/vehicle/register | register user's vehicle | &lt;String, String&gt; | int |
| :large_blue_circle: PUT | /rest/vehicle/custom/register | register custom vehicle | &lt;Vehicle&gt; | int |
| :green_circle: GET | /rest/vehicle/information | information about user's vehicles | String | List&lt;Vehicle&gt; |
| :yellow_circle: POST | /rest/vehicle/delete | deregistration user's vehicle | String | String |

A websocket approach was implemented for fast, real-time data transmission between the customer and the vehicle.   
Websocket is implemented as multi-channel server-side application with STOMP approach.    
An example implementation of the publisher and subscriber can be found in the `WebSocket_manual_test` folder.    
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

## Tables

A MySQL database was used to store user and vehicle information.  
The entire database is containerised using Docker.  
The JDBC interface has been used to create a connection to the database.

Users table:
| userId | login | password | email | phoneNum | role | accCreated |
| -------------- | -------------- | -------------- | -------------- | -------------- | -------------- | -------------- |
| VARCHAR(36) | VARCHAR(255) | VARCHAR(255) | VARCHAR(255) | INT | VARCHAR(50) | DATETIME |
| randomUUID()  | "mylogin2" | "mypw372!" | "mail@gmail.com" | 632857365 | "user" | "10.10.2023 19:23" |

Vehicles table:
| userId | vehicleId | vehicleName | vehicleType | registrationTime |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| VARCHAR(36)  | VARCHAR(36) | VARCHAR(255) | VARCHAR(50) | DATETIME |
| randomUUID()  | randomUUID() | "Raspberry4drone" | "mobilerobot" | "10.10.2023 19:23" |

## Data transmitted to vehicle

Data sent via websocket is JSON.   
Keywords and types may vary depending on vehicle type used.   
Current state of object is fitting quadcopter data.   
Example values:  

```json
{
  "vehicleId": "e218e18c-9e1c-11ee-8c90-0242ac120002",
  "mode": 2,
  "vtol": 0,
  "x": 1,
  "y": 4,
  "alt": 50,
  "yaw": 0,
  "camTrig": 0,
  "camTog": 0,
  "camPitch": 0,
  "clamp": 0
}
```

mode: 1 - Mild, 2 - Normal, 3 - Sport.   
vtol: 0 - no action, 1 - take off, 2 - landing.
