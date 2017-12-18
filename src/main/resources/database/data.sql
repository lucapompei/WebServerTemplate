-- user data
INSERT INTO users VALUES (1, 'user', '$2a$10$pQcOHG2LY3WyH57Yl86xn.Po8tBlM3d/nLEWeymjX2OwaLYbjDdYy', true);
INSERT INTO users VALUES (2, 'admin', '$2a$10$pQcOHG2LY3WyH57Yl86xn.Po8tBlM3d/nLEWeymjX2OwaLYbjDdYy', true);

-- authorities data
INSERT INTO user_roles VALUES (1, 'ROLE_USER');
INSERT INTO user_roles VALUES (2, 'ROLE_ADMIN');