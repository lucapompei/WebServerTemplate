-- users table
CREATE TABLE IF NOT EXISTS users (
	id int not null generated by default as identity,
 	username VARCHAR(255),
 	password VARCHAR(255),
 	role VARCHAR(255)
);