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

INSERT INTO users (id, username, password, firstName, lastName, enabled, avatar, role_id)
VALUES ('6302a41b-e61f-40a9-9dd0-9eb10028a793', 'admin', '123456', 'admin', 'admin', true, 'avatar1.jpg', (SELECT id FROM roles WHERE name = 'ADMIN'));
