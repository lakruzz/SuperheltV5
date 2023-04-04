USE superhero;

INSERT into city (name) values ('New York');
INSERT into city (name) values ('Gotham city');
INSERT into city (name) values ('Metropolis');


INSERT into superpower (name) values ('Spind');
INSERT into superpower (name) values ('Superstyrke');
INSERT into superpower (name) values ('Millioner');
INSERT into superpower (name) values ('Magi');

INSERT into superhero (hero_name, real_name, creation_year, city_id)
	values('Spider-man', 'Peter Parker', 1962, 1);
INSERT into superhero (hero_name, real_name, creation_year, city_id)
	values('Batman', 'Bruce Wayne', 1939, 2);
INSERT into superhero (hero_name, real_name, creation_year, city_id)
	values('Doctor Strange', 'Dr Stephen Strange', 1963, 1);

INSERT into superhero_powers values (1,1,'high');
INSERT into superhero_powers values (1,3,'low');
INSERT into superhero_powers values (2,2,'high');
INSERT into superhero_powers values (3,4,'high');
commit;
