-- users table
CREATE TABLE IF NOT EXISTS users (
	id INT(11) PRIMARY KEY auto_increment,
 	username VARCHAR(255),
 	password VARCHAR(255),
 	role VARCHAR(255)
);