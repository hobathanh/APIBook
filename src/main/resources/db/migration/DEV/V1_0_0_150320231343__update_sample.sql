DELETE
FROM books
WHERE title IN ('Harry Potter', 'Harry Potter2');

INSERT INTO books (title, author, description, image, user_id)
VALUES ('Harry Potter and the Sorcerer Stone', 'J. K. Rowling',
        'Harry Potter is a seven-part series of paranormal novels by English writer J. K. Rowling', 'image_Book1.jpg',
        (SELECT id FROM users WHERE username = 'thanh'));

INSERT INTO books (title, author, description, image, user_id)
VALUES ('harry potter and the philosopher stone', 'J. K. Rowling', 'Writer J. K. Rowling', 'image_Book2.jpg',
        (SELECT id FROM users WHERE username = 'thanh'));