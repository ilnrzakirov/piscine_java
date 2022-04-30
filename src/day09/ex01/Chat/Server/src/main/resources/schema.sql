drop table if exists users cascade;
drop table if exists message cascade;
CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, username varchar(15) UNIQUE NOT NULL, password TEXT not null );
CREATE TABLE IF NOT EXISTS message (id SERIAL PRIMARY KEY, dateTime TIMESTAMP , text TEXT);