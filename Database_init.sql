USE crudapp;

-- === users table ===
-- create table
CREATE TABLE IF NOT EXISTS  crudapp.users (
	userId VARCHAR(36) PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phoneNum BIGINT NOT NULL,
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
    userId VARCHAR(36),
    vehicleId VARCHAR(36) PRIMARY KEY,
    vehicleName VARCHAR(255) NOT NULL,
    vehicleType VARCHAR(50) NOT NULL,
    registrationTime DATETIME NOT NULL,
    FOREIGN KEY (`userId`) REFERENCES `crudapp`.`users`(`userId`)
);
-- clear table
TRUNCATE TABLE vehicles;
-- insert example data
INSERT INTO `crudapp`.`vehicles` (userId, vehicleId, vehicleName, vehicleType, registrationTime)
VALUES (
    'b8aa0ab7-9c79-11ee-89e3-0242ac110002',    -- function for MySQL 8.0+, it have to exist in 'users' table
    UUID(),    -- vehicleId
    'MyDrone1',
    'quadcopter',
    CONVERT_TZ(NOW(), 'UTC', 'Europe/Warsaw')
);
-- display table
SELECT * FROM vehicles;

-- === quadcopters table ===
-- create table
CREATE TABLE IF NOT EXISTS crudapp.quadcopters (
    vehicleId VARCHAR(36) PRIMARY KEY,
    mode INT NOT NULL,
    vtol INT NOT NULL,
    x INT NOT NULL,
    y INT NOT NULL,
    alt INT NOT NULL,
    yaw INT NOT NULL,
    camTrig BOOLEAN  NOT NULL,
    camTog BOOLEAN  NOT NULL,
    camPitch INT NOT NULL,
    clamp BOOLEAN  NOT NULL,
    FOREIGN KEY (`vehicleId`) REFERENCES `crudapp`.`vehicles`(`vehicleId`)
);
-- clear table
TRUNCATE TABLE vehicles;
-- insert example data
INSERT INTO `crudapp`.`quadcopters` (vehicleId, mode, vtol, x, y, alt, yaw, camTrig, camTog, camPitch, clamp)
VALUES (
    '0d1e43a7-9c7a-11ee-89e3-0242ac110002',    -- vehicleId
    1,
    0,
    1,
    0,
    0,
    0,
    0,
    1,
    0,
    0
);
-- display table
SELECT * FROM quadcopters;


-- delete table
DROP TABLE users;
DROP TABLE vehicles;
DROP TABLE quadcopters;