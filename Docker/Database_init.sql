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
    userId VARCHAR(36) NULL,
    vehicleId VARCHAR(36) PRIMARY KEY,
    vehicleName VARCHAR(255) NOT NULL,
    vehicleType VARCHAR(50) NOT NULL,
    registrationTime DATETIME NULL,
    FOREIGN KEY (`userId`) REFERENCES `robotTaskerApi`.`users`(`userId`)
);
