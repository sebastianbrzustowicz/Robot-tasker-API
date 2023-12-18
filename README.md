## Execute app

Initializing app is possible with this command:
```
mvn spring-boot:run
```

## Endpoints

Client endpoints for user's data:

| HTTP method | endpoint | description | request type | response type |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| :yellow_circle: POST | /register | register new user | User | int |
| :red_circle: DELETE | /user/delete/{userId} | delete user | String | int |
| :yellow_circle: POST | /login | login user | List<String> | String |
| :yellow_circle: POST | /user/vehicle/register/{vehicleId} | register user's vehicle | String | int |
| :green_circle: GET | /user/vehicle/information/{userId} | information about user's vehicles | String | List<Vehicle> |
| :red_circle: DELETE | /user/vehicle/delete/{vehicleId} | deregistration user's vehicle | List<String> | int |
| :yellow_circle: POST | /user/vehicle/connect/{vehicleId} | connect to user's vehicle | int | int |
| :red_circle: DELETE | /user/vehicle/disconnect/{vehicleId} | disconnect from user's vehicle | int | int |

Response type is mostly number of rows affected. 

Client endpoints based on specific vehicle type (quadcopter in this case):

| HTTP method | endpoint | description | request type | response type |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| :large_blue_circle: PUT | /runtime/{vehicleId}/data/update | initialize data/send all data | List<int> | int |
| :purple_circle: PATCH | /runtime/{vehicleId}/data/vtol/{value} | take off/landing | List<int> | int |
| :purple_circle: PATCH | /runtime/{vehicleId}/data/xyaltyaw{value}  | flight data update | List<int> | int |
| :purple_circle: PATCH | /runtime/{vehicleId}/data/camtrigger{value}  | make photo, start/stop recording | List<Object> | int |
| :purple_circle: PATCH | /runtime/{vehicleId}/data/camtoggle{value}  | camera/video toggle | List<Object> | int |
| :purple_circle: PATCH | /runtime/{vehicleId}/data/campitch{value}  | camera pitch angular speed | List<int> | int |
| :purple_circle: PATCH | /runtime/{vehicleId}/data/payloadtoggle{value}  | payload clamp on/off | List<Object> | int |
| :purple_circle: PATCH | /runtime/{vehicleId}/data/mode{value}  | flight mode | List<int> | int |

Response type is mostly number of rows affected. 

Vehicle endpoints for data transmition:

| HTTP method | endpoint | description | request type | response type |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| :green_circle: GET | /robot/{vehicleId}/data | get information about flight data | int | List |
| :purple_circle: PATCH | /robot/{vehicleId}/data/alt/{value} | altitude feedback | List<int> | null |

## Tables

Users table:
| userId | login | password | email | phoneNum | role | accCreated |
| -------------- | -------------- | -------------- | -------------- | -------------- | -------------- | -------------- |
| &lt;VARCHAR(36)&gt; | &lt;VARCHAR(255)&gt; | &lt;VARCHAR(255)&gt; | &lt;VARCHAR(255)&gt; | &lt;INT&gt; | &lt;VARCHAR(50)&gt; | &lt;DATETIME&gt; |
| randomUUID()  | "mylogin2" | "mypw372!" | "mail@gmail.com" | 632857365 | "user" | "10.10.2023 19:23" |

Vehicles table:
| userId | vehicleId | vehicleName | vehicleType | registrationTime |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| &lt;VARCHAR(36)&gt;  | &lt;VARCHAR(36)&gt; | &lt;VARCHAR(255)&gt; | &lt;VARCHAR(50)&gt; | &lt;DATETIME&gt; |
| randomUUID()  | randomUUID() | "Raspberry4drone" | "mobilerobot" | "10.10.2023 19:23" |

Quadcopters table:
| vehicleId | mode | vtol | x | y | alt | yaw | camTrig | camTog | camPitch | clamp |
| -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- |
| &lt;VARCHAR(36)&gt; | &lt;INT&gt; | &lt;INT&gt; | &lt;INT&gt; | &lt;INT&gt; | &lt;INT&gt; | &lt;INT&gt; | &lt;BOOLEAN&gt; | &lt;BOOLEAN&gt; | &lt;INT&gt; | &lt;BOOLEAN&gt; |
| randomUUID() | 2 | 1 | 2 | 5 | 3 | 1 | 1 | 0 | 3 | 1 |

flightMode: 1 - Mild, 2 - Normal, 3 - Sport.   
vtol: 0 - no action, 1 - take off, 2 - landing.
