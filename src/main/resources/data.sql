INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users (name, username, password) VALUES ('admin', 'admin', 'admin');
INSERT INTO users (name, username, password) VALUES ('user', 'user', 'user');

INSERT INTO users_roles (user_id, roles_id) VALUES
                                                ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')),
                                                ((SELECT id FROM users WHERE username = 'user'), (SELECT id FROM roles WHERE name = 'ROLE_USER'));