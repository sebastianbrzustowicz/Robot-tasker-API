USE robotTaskerApi;

-- === users table ===
-- create table
CREATE TABLE IF NOT EXISTS  robotTaskerApi.users (
	userId VARCHAR(36) PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phoneNum INT NOT NULL,
    role VARCHAR(50) NOT NULL,
    accCreated DATETIME NOT NULL
);
-- === vehicles table ===
-- create table
CREATE TABLE IF NOT EXISTS robotTaskerApi.vehicles (
	vehicleId VARCHAR(36) PRIMARY KEY,
    userId VARCHAR(36) NULL,
    vehicleName VARCHAR(255) NOT NULL,
    vehicleType VARCHAR(50) NOT NULL,
    registrationTime DATETIME NULL,
    FOREIGN KEY (`userId`) REFERENCES `robotTaskerApi`.`users`(`userId`)
);

-- insert example data
INSERT INTO robotTaskerApi.users (userId, login, password, email, phoneNum, role, accCreated) VALUES
  ('1', 'user1', 'password1', 'user1@example.com', 123456789, 'user', '2022-01-01 10:00:00'),
  ('2', 'user2', 'password2', 'user2@example.com', 987654321, 'admin', '2022-01-02 11:30:00'),
  ('3', 'user3', 'password3', 'user3@example.com', 555555555, 'user', '2022-01-03 14:45:00');

INSERT INTO robotTaskerApi.vehicles (vehicleId, userId, vehicleName, vehicleType, registrationTime) VALUES
  ('101', '1', 'Car1', 'Sedan', '2022-01-05 08:30:00'),
  ('102', '2', 'Motorcycle1', 'Motorcycle', '2022-01-06 10:15:00'),
  ('103', '1', 'Truck1', 'Truck', '2022-01-07 12:45:00');

-- insert example data
INSERT INTO robotTaskerApi.vehicles (vehicleId, userId, vehicleName, vehicleType, registrationTime)
VALUES (
    UUID(),    -- vehicleId
	null,    -- function for MySQL 8.0+
    'MyDrone2',
    'quadcopter',
    null
    -- CONVERT_TZ(NOW(), 'UTC', 'Europe/Warsaw')
);
-- display table
SELECT * FROM robotTaskerApi.users;
SELECT * FROM robotTaskerApi.vehicles;

-- clear table
TRUNCATE TABLE robotTaskerApi.users;
TRUNCATE TABLE robotTaskerApi.vehicles;

-- delete table
DROP TABLE robotTaskerApi.users;
DROP TABLE robotTaskerApi.vehicles;

-- apply changes
UPDATE robotTaskerApi.vehicles
SET
  userId = null,
  registrationTime = null
WHERE
  vehicleId = '39726ba8-9d4a-11ee-8070-0242ac110002';