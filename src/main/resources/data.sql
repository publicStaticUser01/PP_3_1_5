INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users (username, password, age, name, last_name) VALUES ('admin', 'admin', 23, 'admin', 'admin');
INSERT INTO users (username, password, age, name, last_name) VALUES ('user', 'user', 23, 'user', 'user');

INSERT INTO users_roles (user_id, roles_id) VALUES
                                                ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')),
                                                ((SELECT id FROM users WHERE username = 'user'), (SELECT id FROM roles WHERE name = 'ROLE_USER'));