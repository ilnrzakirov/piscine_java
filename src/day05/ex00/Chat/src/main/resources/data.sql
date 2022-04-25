INSERT INTO chat.users (login, password)
VALUES ('Jack', 'jaja');
INSERT INTO chat.users (login, password)
VALUES ('Tim', 'titi');
INSERT INTO chat.users (login, password)
VALUES ('Tom', 'toto');
INSERT INTO chat.users (login, password)
VALUES ('John', 'jojo');
INSERT INTO chat.users (login, password)
VALUES ('Ben', 'bebe');

INSERT INTO chat.rooms (name, owner)
VALUES ('car', 1);
INSERT INTO chat.rooms (name, owner)
VALUES ('hobby', 2);
INSERT INTO chat.rooms (name, owner)
VALUES ('sport', 3);
INSERT INTO chat.rooms (name, owner)
VALUES ('stalker', 4);
INSERT INTO chat.rooms (name, owner)
VALUES ('animals', 5)


INSERT INTO chat.message (author, chatroom, text, time)
VALUES (1, 1, 'BMW car th', current_timestamp);
INSERT INTO chat.message (author, chatroom, text, time)
VALUES (2, 2, 'painter other s', current_timestamp);
INSERT INTO chat.message (author, chatroom, text, time)
VALUES (3, 3, 'bol sdf', current_timestamp);
INSERT INTO chat.message (author, chatroom, text, time)
VALUES (4, 4, 'comp stalker', current_timestamp);
INSERT INTO chat.message (author, chatroom, text, time)
VALUES (5, 5, 'cat and dog', current_timestamp);
INSERT INTO chat.message (author, chatroom, text, time)
VALUES (5, 5, 'horse and dog', current_timestamp);