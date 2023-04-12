INSERT INTO users (username, password, first_name, last_name, enabled, avatar, role_id)
VALUES ('hobathanh201@gmail.com', '$2a$12$3GjmcPUu7PpwwBreqaD4quYa.ZzHOjHtI7ZFbWV.i9MVwfIUTuusS',
        'ho', 'thanh', true, 'avatar5.jpg',
        (SELECT id FROM roles WHERE name = 'CONTRIBUTOR'));