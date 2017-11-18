-- users table
CREATE TABLE users (
	id INT(11) PRIMARY KEY,
 	username VARCHAR(255),
 	password VARCHAR(255),
 	enabled INT(11)
);

-- user_roles table
CREATE TABLE user_roles (
 	id_user  INT(11) REFERENCES users (id),
  	role VARCHAR(255)
);