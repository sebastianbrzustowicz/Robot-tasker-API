USE crudapp;

-- === users table ===
-- create table
CREATE TABLE IF NOT EXISTS  crudapp.users (
	userId VARCHAR(36) PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phoneNum INT NOT NULL,
    role VARCHAR(50) NOT NULL,
    accCreated DATETIME NOT NULL
);
-- clear table
TRUNCATE TABLE users;
-- insert example data
INSERT INTO users (userId, login, password, email, phoneNum, role, accCreated)
VALUES (
    UUID(),    -- function for MySQL 8.0+
    'mylogin',
    'mypw658!',
    'example@gmail.com',
    638179534,
    'admin',
    CONVERT_TZ(NOW(), 'UTC', 'Europe/Warsaw')
);
-- display table
SELECT * FROM users;

-- === vehicles table ===
-- create table
CREATE TABLE IF NOT EXISTS crudapp.vehicles (
    userId VARCHAR(36) NULL,
    vehicleId VARCHAR(36) PRIMARY KEY,
    vehicleName VARCHAR(255) NOT NULL,
    vehicleType VARCHAR(50) NOT NULL,
    registrationTime DATETIME NULL,
    FOREIGN KEY (`userId`) REFERENCES `crudapp`.`users`(`userId`)
);
-- clear table
TRUNCATE TABLE vehicles;
-- insert example data
INSERT INTO `crudapp`.`vehicles` (userId, vehicleId, vehicleName, vehicleType, registrationTime)
VALUES (
    null,    -- function for MySQL 8.0+
    UUID(),    -- vehicleId
    'MyDrone2',
    'quadcopter',
    null
    -- CONVERT_TZ(NOW(), 'UTC', 'Europe/Warsaw')
);
-- display table
SELECT * FROM vehicles;
-- apply changes
UPDATE crudapp.vehicles
SET
  `userId` = null,
  `registrationTime` = null
WHERE
  `vehicleId` = '39726ba8-9d4a-11ee-8070-0242ac110002';

-- delete table
DROP TABLE users;
DROP TABLE vehicles;