DROP DATABASE IF EXISTS superhero;
CREATE DATABASE IF NOT EXISTS superhero DEFAULT CHARACTER SET UTF8MB4;
USE superhero;

DROP TABLE if exists city;
DROP TABLE if exists superpower;
DROP TABLE if exists superhero;
DROP TABLE if exists superhero_powers;

CREATE TABLE city (
	city_id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	PRIMARY KEY (city_id),
    UNIQUE INDEX (name)
);

CREATE TABLE superpower (
	power_id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	PRIMARY KEY (power_id),
	UNIQUE INDEX (name)
);

CREATE TABLE superhero (
	hero_id INTEGER NOT NULL AUTO_INCREMENT,
	hero_name VARCHAR(50) NOT NULL,
	real_name VARCHAR(50) NOT NULL,
	creation_year INT,
	city_id INTEGER,
	PRIMARY KEY (hero_id),
	FOREIGN KEY (city_id) REFERENCES city(city_id),
    UNIQUE INDEX (hero_name)
);

CREATE TABLE superhero_powers (
	hero_id INTEGER NOT NULL,
	power_id INTEGER NOT NULL,
	LEVEL ENUM ('low', 'medium', 'high'),
	PRIMARY KEY (hero_id, power_id),
	FOREIGN KEY (hero_id) REFERENCES superhero(hero_id),
	FOREIGN KEY (power_id) REFERENCES superpower(power_id)
);