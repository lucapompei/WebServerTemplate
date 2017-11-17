-- users table
CREATE TABLE users (
	id INT(11) PRIMARY KEY,
 	username VARCHAR(255),
 	email VARCHAR(255),
 	password VARCHAR(255)
);

-- authorities table
CREATE TABLE authorities (
 	id_user  INT(11) REFERENCES users (id),
  	authority VARCHAR(255)
);