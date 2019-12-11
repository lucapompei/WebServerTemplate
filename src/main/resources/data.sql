-- insert user if not present
INSERT INTO users (username, password, role)
SELECT * FROM (SELECT 'user', '$2a$10$pQcOHG2LY3WyH57Yl86xn.Po8tBlM3d/nLEWeymjX2OwaLYbjDdYy', 'ROLE_USER') AS tmp
WHERE NOT EXISTS (
    SELECT * FROM users WHERE username = 'user'
) LIMIT 1;

-- insert admin if not present
INSERT INTO users (username, password, role)
SELECT * FROM (SELECT 'admin', '$2a$10$pQcOHG2LY3WyH57Yl86xn.Po8tBlM3d/nLEWeymjX2OwaLYbjDdYy', 'ROLE_ADMIN') AS tmp
WHERE NOT EXISTS (
    SELECT * FROM users WHERE username = 'admin'
) LIMIT 1;