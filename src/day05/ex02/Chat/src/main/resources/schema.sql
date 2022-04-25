DROP SCHEMA IF EXISTS chat CASCADE;
drop table if exists chat.messages cascade;
drop table if exists chat.chatrooms cascade;
drop table if exists chat.users cascade;
CREATE SCHEMA IF NOT EXISTS chat;
CREATE TABLE IF NOT EXISTS chat.users (id  SERIAL PRIMARY KEY, login varchar(15) UNIQUE NOT NULL, password varchar(15) NOT NULL);
CREATE TABLE IF NOT EXISTS chat.rooms (id SERIAL PRIMARY KEY, name TEXT UNIQUE NOT NULL, owner INTEGER REFERENCES chat.users(id) NOT NULL);
CREATE TABLE IF NOT EXISTS  chat.messages (id SERIAL PRIMARY KEY, author INTEGER REFERENCES chat.users(id) NOT NULL,chatroom INTEGER REFERENCES chat.rooms(id) NOT NULL, text TEXT NOT NULL, time TIMESTAMP NOT NULL);
