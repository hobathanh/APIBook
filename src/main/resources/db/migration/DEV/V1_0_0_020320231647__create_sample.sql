INSERT INTO users (username, password, first_name, last_name, enabled, avatar, role_id)
VALUES ('thanh', '123456', 'ba', 'thanh', true, 'avatar2.jpg', (SELECT id FROM roles WHERE name = 'CONTRIBUTOR'));

INSERT INTO books (title, author, description, created_at, updated_at, image, user_id)
VALUES ('Harry Potter', 'J. K. Rowling',
        'Harry Potter is a seven-part series of paranormal novels by English writer J. K. Rowling',
        '2023-03-02 12:00:00', '2023-03-02 12:00:00', 'image_Book1.jpg',
        (SELECT id FROM users WHERE username = 'thanh'));
INSERT INTO books (title, author, description, created_at, updated_at, image, user_id)
VALUES ('Harry Potter2', 'J. K. Rowling', 'Writer J. K. Rowling',
        '2023-03-02 12:02:00', '2023-03-02 12:02:00', 'image_Book2.jpg',
        (SELECT id FROM users WHERE username = 'thanh'));
