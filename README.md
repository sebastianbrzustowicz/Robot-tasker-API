## Endpoints

Client endpoints for user's data:

| HTTP method | endpoint | description | request type | response type |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| :yellow_circle: POST | /register | register new user | List<String> | null |
| :red_circle: DELETE | /user/delete/{userId} | delete user | int | null |
| :yellow_circle: POST | /login | login user | List<String> | null |
| :yellow_circle: POST | /user/vehicle/register/{vehicleId} | register vehicle row with new id and user data | int | null |
| :red_circle: DELETE | /user/vehicle/delete/{vehicleId} | delete vehicle by specific userId | int | null |
| :green_circle: GET | /user/vehicle/information | get information about vehicles | int | List |
| :yellow_circle: POST | /user/vehicle/connect/{vehicleId} | connect to user's vehicle | int | null |
| :red_circle: DELETE | /user/vehicle/disconnect/{vehicleId} | disconnect from user's vehicle | int | null |

Client endpoints based on specific vehicle type (quadcopter in this case):

| HTTP method | endpoint | description | request type | response type |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| :large_blue_circle: PUT | /runtime/{vehicleId}/data/update | initialize data/send all data | List<int> | null |
| :purple_circle: PATCH | /runtime/{vehicleId}/data/vtol/{value} | take off/landing | List<int> | null |
| :purple_circle: PATCH | /runtime/{vehicleId}/data/xyaltyaw{value}  | flight data update | List<int> | null |
| :purple_circle: PATCH | /runtime/{vehicleId}/data/camtrigger{value}  | make photo, start/stop recording | List<int> | null |
| :purple_circle: PATCH | /runtime/{vehicleId}/data/camtoggle{value}  | camera/video toggle | List<int> | null |
| :purple_circle: PATCH | /runtime/{vehicleId}/data/campitch{value}  | camera pitch angular speed | List<int> | null |
| :purple_circle: PATCH | /runtime/{vehicleId}/data/payloadtoggle{value}  | payload clamp on/off | List<int> | null |
| :purple_circle: PATCH | /runtime/{vehicleId}/data/mode{value}  | flight mode | List<int> | null |

Vehicle endpoints for data transmition:

| HTTP method | endpoint | description | request type | response type |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| :green_circle: GET | /robot/{vehicleId}/data | get information about flight data | int | List |
| :purple_circle: PATCH | /robot/{vehicleId}/data/alt/{value} | altitude feedback | List<int> | null |

## Tables

Users table:
| userId | login | password | email | phoneNum | role | accCreated |
| -------------- | -------------- | -------------- | -------------- | -------------- | -------------- | -------------- |
| 745339102  | "mylogin" | mypw658! | "example@gmail.com" | 638179534 | "admin" | "01.12.2023 09:18" |
| 753534612  | "mylogin2" | mypw372! | "mail@gmail.com" | 632857365 | "user" | "10.10.2023 19:23" |

Vehicles table:
| userId | vehicleId | vehicleName | vehicleType | registrationTime |
| -------------- | -------------- | -------------- | -------------- | -------------- |
| 745339102  | 531365234 | "MyDrone1" | "quadcopter" | "01.12.2023 09:18" |
| 753534612  | 826391697 | "Raspberry4drone" | "mobilerobot" | "10.10.2023 19:23" |

Quadcopters table:
| vehicleId | mode | vtol | x | y | alt | yaw | camTrig | camTog | camPitch | clamp |
| -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- |
| 531365234 | 1 | 0 | 1 | 0 | 0 | 0 | 0 | 1 | 0 | 0 |
| 826391697 | 2 | 1 | 2 | 5 | 3 | 1 | 1 | 0 | 3 | 1 |

flightMode: 1 - Mild, 2 - Normal, 3 - Sport.   
vtol: 0 - no action, 1 - take off, 2 - landing.
