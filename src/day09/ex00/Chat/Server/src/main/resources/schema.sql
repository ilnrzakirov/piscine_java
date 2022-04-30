drop table if exists users cascade;
CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, username varchar(15) UNIQUE NOT NULL, password TEXT not null );