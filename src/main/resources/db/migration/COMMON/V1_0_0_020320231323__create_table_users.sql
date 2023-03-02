CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users
(
    id UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    enabled BOOLEAN NOT NULL,
    avatar VARCHAR(255),
    role_id UUID NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

INSERT INTO users (username, password, firstName, lastName, enabled, avatar, role_id)
VALUES ('admin', '123456', 'admin', 'admin', true, 'avatar1.jpg', (SELECT id FROM roles WHERE name = 'Admin'));

INSERT INTO users (username, password, firstName, lastName, enabled, avatar, role_id)
VALUES ('thanh', '123456', 'ba', 'thanh', true, 'avatar2.jpg', (SELECT id FROM roles WHERE name = 'Contributor'));