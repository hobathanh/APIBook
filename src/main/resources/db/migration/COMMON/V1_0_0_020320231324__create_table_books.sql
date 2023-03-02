CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE books
(
      id UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
      title VARCHAR(255) NOT NULL,
      author VARCHAR(255) NOT NULL,
      description VARCHAR(255),
      created_at TIMESTAMP NOT NULL ,
      updated_at TIMESTAMP NOT NULL,
      image VARCHAR(255),
      user_id UUID NOT NULL,
      FOREIGN KEY (user_id) REFERENCES users(id)
);
