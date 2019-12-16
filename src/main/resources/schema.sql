-- users table
CREATE TABLE IF NOT EXISTS users (
 	username VARCHAR(255) PRIMARY KEY,
 	password VARCHAR(255),
 	role VARCHAR(255)
);