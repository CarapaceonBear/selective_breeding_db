DROP TABLE dragons IF EXISTS;
CREATE TABLE IF NOT EXISTS dragons (
id integer PRIMARY KEY AUTO_INCREMENT,
name varchar(255),
sex varchar(255),
colour varchar(255),
generation integer,
scaleQuality double,
flyingSpeed double,
eggSize double,
eggQuality double,
breathTemperature double,
PRIMARY KEY (id)
