## Endpoints

Data to transmit in json:

| HTTP method   | endpoint      | description | type |
| ------------- | ------------- | ----------- | ---- |
| :green_circle: GET  | /user/information | get information about vehicle before flight | List |
| :yellow_circle: POST  | /user/create | create drone row with new id and user data | List |
| :red_circle: DELETE  | /user/delete/{id} | delete vehicle by specific id | int |
| :yellow_circle: POST  | /user/login | authorization - connect to specific drone | List |
| :green_circle: GET  | /{id}/quadcopter/user/data | get information about flight data | List |
| :purple_circle: PATCH  | /{id}/quadcopter/user/data/mode | flight mode | String |
| :purple_circle: PATCH  | /user/{id}/quadcopter/user/data/vtol | take off/landing | String |
| :large_blue_circle: PUT  | /{id}/quadcopter/user/data/xyaltyaw | flight mode data update | List |

Optional data to transmit in json:

| HTTP method   | endpoint      | description | type |
| ------------- | ------------- | ----------- | ---- |
| :purple_circle: PATCH  | /{id}/quadcopter/user/data/camtrigger | make photo, start/stop recording | boolean |
| :purple_circle: PATCH  | /{id}/quadcopter/user/data/camtoggle | camera/video toggle | boolean |
| :purple_circle: PATCH  | /{id}/quadcopter/user/data/campitch | camera pitch angular speed | int |
| :purple_circle: PATCH  | /{id}/quadcopter/user/data/payloadtoggle | payload clamp on/off | boolean |
