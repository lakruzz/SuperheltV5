DROP DATABASE IF EXISTS SUPERHERO;
CREATE DATABASE IF NOT EXISTS SUPERHERO DEFAULT CHARACTER SET UTF8MB4;
USE SUPERHERO;

DROP TABLE if exists CITY;
DROP TABLE if exists SUPERPOWER;
DROP TABLE if exists SUPERHERO;
DROP TABLE if exists SUPERHERO_POWERS;

CREATE TABLE CITY (
	CITY_ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME VARCHAR(50) NOT NULL,
	PRIMARY KEY (CITY_ID),
    UNIQUE INDEX (NAME)
);

CREATE TABLE SUPERPOWER (
	POWER_ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME VARCHAR(50) NOT NULL,
	PRIMARY KEY (POWER_ID),
	UNIQUE INDEX (NAME)
);

CREATE TABLE SUPERHERO (
	HERO_ID INTEGER NOT NULL AUTO_INCREMENT,
	HERO_NAME VARCHAR(50) NOT NULL,
	REAL_NAME VARCHAR(50) NOT NULL,
	CREATION_YEAR INT,
	CITY_ID INTEGER,
	PRIMARY KEY (HERO_ID),
	FOREIGN KEY (CITY_ID) REFERENCES CITY(CITY_ID),
    UNIQUE INDEX (HERO_NAME)
);

CREATE TABLE SUPERHERO_POWERS (
	HERO_ID INTEGER NOT NULL,
	POWER_ID INTEGER NOT NULL,
	LEVEL ENUM ('low', 'medium', 'high'),
	PRIMARY KEY (HERO_ID, POWER_ID),
	FOREIGN KEY (HERO_ID) REFERENCES SUPERHERO(HERO_ID),
	FOREIGN KEY (POWER_ID) REFERENCES SUPERPOWER(POWER_ID)
);

INSERT into CITY (name) values ('New York');
INSERT into CITY (name) values ('Gotham city');
INSERT into CITY (name) values ('Metropolis');


INSERT into SUPERPOWER (name) values ('Spind');
INSERT into SUPERPOWER (name) values ('Superstyrke');
INSERT into SUPERPOWER (name) values ('Millioner');
INSERT into SUPERPOWER (name) values ('Magi');

INSERT into SUPERHERO (hero_name, real_name, creation_year, city_id)
	values('Spider-man', 'Peter Parker', 1962, 1);
INSERT into SUPERHERO (hero_name, real_name, creation_year, city_id)
	values('Batman', 'Bruce Wayne', 1939, 2);
INSERT into SUPERHERO (hero_name, real_name, creation_year, city_id)
	values('Doctor Strange', 'Dr Stephen Strange', 1963, 1);

INSERT into SUPERHERO_POWERS values (1,1,'high');
INSERT into SUPERHERO_POWERS values (1,3,'low');
INSERT into SUPERHERO_POWERS values (2,2,'high');
INSERT into SUPERHERO_POWERS values (3,4,'high');
commit;

select count(*) superhero;

select hero_name, real_name, CREATION_YEAR from superhero
order by CREATION_YEAR desc;

select hero_name, real_name, CREATION_YEAR from superhero
where CREATION_YEAR = (select min(CREATION_YEAR) from superhero);

select HERO_NAME, CREATION_YEAR from superhero where CREATION_YEAR > '1960-01-01';

select HERO_NAME, count(*) from superhero join superhero_powers using (hero_id)
group by hero_name
having count(*) > 1;

select HERO_NAME, count(*) from superhero join superhero_powers using (hero_id)
group by hero_name;

select hero_name, real_name, name as power, level from superhero join superhero_powers using (hero_id) join superpower using (power_id);
select name from city;

select name from city where name like 'n%';

select hero_name, name from city join superhero using (city_id);

select count(*) from city join superhero using (city_id)
where name = 'new york';

select distinct(name) from city join superhero using (city_id)
where hero_name like '%a%';

# Opgave Superhelte v.4
# Sql statements
SELECT * FROM superhero.superhero;
SELECT * FROM superhero.city;
SELECT * FROM superhero.superpower;
SELECT * FROM superhero.superhero_powers;

# Q1
select hero_id, hero_name, real_name, CREATION_YEAR from superhero
order by HERO_NAME;

# Q2
select HERO_ID, HERO_NAME, REAL_NAME, COUNT(*) from superhero join superhero_powers using (hero_id)
group by hero_name;

# Q3
select HERO_ID, HERO_NAME, REAL_NAME, name as superpower from superhero join superhero_powers using (hero_id) join superpower using (power_id);

# Q4
select city.city_id, name as city, HERO_NAME from superhero join city where superhero.CITY_ID = city.CITY_ID
order by name;
