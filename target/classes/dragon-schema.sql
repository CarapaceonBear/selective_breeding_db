DROP TABLE IF EXISTS dragon CASCADE;
CREATE TABLE dragon (
id integer AUTO_INCREMENT,
name varchar(255),
sex varchar(255),
generation integer,
colour varchar(255),
scale_quality float,
flying_speed float,
egg_size float,
egg_quality float,
breath_temperature float,
PRIMARY KEY (id)
);