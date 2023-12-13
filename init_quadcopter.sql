USE crudapp;

CREATE TABLE IF NOT EXISTS  `crudapp`.`quadcopter` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `mode` VARCHAR(200) NULL,
  `altitude` INT NULL,
  PRIMARY KEY (`id`));

TRUNCATE TABLE quadcopter;

INSERT INTO quadcopter(mode, altitude) VALUES('Sport', 10);

SELECT * FROM quadcopter;