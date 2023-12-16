## Endpoints

Data to transmit in json:

| HTTP method | endpoint | description | type |
| -------------- | -------------- | -------------- | -------------- |
| :green_circle: GET  | /user/information | get information about vehicle before flight | List |
| :yellow_circle: POST  | /user/create | create drone row with new id and user data | List |
| :red_circle: DELETE  | /user/delete/{id} | delete vehicle by specific id | int |
| :yellow_circle: POST  | /user/login | authorization - connect to specific drone | List |
| :green_circle: GET  | /{id}/quadcopter/user/data | get information about flight data | List |
| :purple_circle: PATCH  | /{id}/quadcopter/user/data/mode | flight mode | String |
| :purple_circle: PATCH  | /user/{id}/quadcopter/user/data/vtol | take off/landing | String |
| :large_blue_circle: PUT  | /{id}/quadcopter/user/data/xyaltyaw | flight mode data update | List |

Optional data to transmit in json:

| HTTP method | endpoint | description | type |
| -------------- | -------------- | -------------- | -------------- |
| :purple_circle: PATCH  | /{id}/quadcopter/user/data/camtrigger | make photo, start/stop recording | boolean |
| :purple_circle: PATCH  | /{id}/quadcopter/user/data/camtoggle | camera/video toggle | boolean |
| :purple_circle: PATCH  | /{id}/quadcopter/user/data/campitch | camera pitch angular speed | int |
| :purple_circle: PATCH  | /{id}/quadcopter/user/data/payloadtoggle | payload clamp on/off | boolean |

## Tables

Users table:
| id | login | password | email | phoneNumber | accCreated |
| -------------- | -------------- | -------------- | -------------- | -------------- | -------------- |
| 745339102  | "mylogin" | mypassword658! | "example@gmail.com" | 638179534 | "01.12.2023 09:18" |
| 753534612  | "mylogin2" | mypassword658!2 | "example2@gmail.com" | 632857365 | "10.10.2023 19:23" |

Vehicles table:
| id | vehicleId | vehicleName | registrationTime |
| -------------- | -------------- | -------------- | -------------- |
| 745339102  | 531365234 | "MyDrone1" | "01.12.2023 09:18" |
| 753534612  | 826391697 | "Raspberry4drone" | "10.10.2023 19:23" |

Runtime table:
| vehicleId | mode | vtol | x | y | alt | yaw | camTrig | camTog | camPitch | clamp |
| -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- |
| 531365234 | 1 | 0 | 1 | 0 | 0 | 0 | 0 | 1 | 0 | 0 |
| 826391697 | 2 | 1 | 2 | 5 | 3 | 1 | 1 | 0 | 3 | 1 |

flightMode: 1 - Mild, 2 - Normal, 3 - Sport.
vtol: 0 - no action, 1 - take off, 2 - landing.
