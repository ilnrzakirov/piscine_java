drop table if exists product CASCADE;
CREATE TABLE IF NOT EXISTS product (id INTEGER IDENTITY PRIMARY KEY, name varchar(20) NOT NULL, price INTEGER NOT NULL);