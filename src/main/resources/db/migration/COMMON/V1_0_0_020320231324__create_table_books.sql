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

INSERT INTO books (title, author, description, created_at, updated_at, image, user_id)
VALUES ('Harry Potter', 'J. K. Rowling', 'Harry Potter is a seven-part series of paranormal novels by English writer J. K. Rowling',
        '2023-03-02 12:00:00', '2023-03-02 12:00:00', 'image_Book1.jpg', (SELECT id FROM users WHERE username = 'thanh'));
INSERT INTO books (title, author, description, created_at, updated_at, image, user_id)
VALUES ('Harry Potter2', 'J. K. Rowling', 'Writer J. K. Rowling',
        '2023-03-02 12:02:00', '2023-03-02 12:02:00', 'image_Book2.jpg', (SELECT id FROM users WHERE username = 'thanh'));
