-- user data
INSERT INTO users VALUES (1, 'user', 'password', true);
INSERT INTO users VALUES (2, 'admin', 'password', true);

-- authorities data
INSERT INTO user_roles VALUES (1, 'ROLE_USER');
INSERT INTO user_roles VALUES (2, 'ROLE_ADMIN');