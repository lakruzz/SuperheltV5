USE superhero;

select count(*) superhero;

select hero_name, real_name, creation_year from superhero
order by creation_year desc;

select hero_name, real_name, creation_year from superhero
where creation_year = (select min(creation_year) from superhero);

select hero_name, creation_year from superhero where creation_year > '1960-01-01';

select hero_name, count(*) from superhero join superhero_powers using (hero_id)
group by hero_name
having count(*) > 1;

select hero_name, count(*) from superhero join superhero_powers using (hero_id)
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
select hero_id, hero_name, real_name, creation_year from superhero
order by hero_name;

# Q2
select hero_id, hero_name, real_name, COUNT(*) from superhero join superhero_powers using (hero_id)
group by hero_name;

# Q3
select hero_id, hero_name, real_name, name as superpower from superhero join superhero_powers using (hero_id) join superpower using (power_id);

# Q4
select city.city_id, name as city, hero_name from superhero join city where superhero.city_id = city.city_id
order by name;
