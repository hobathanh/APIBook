INSERT INTO users (id, username, password, first_name, last_name, enabled, avatar, role_id)
VALUES ('6302a41b-e61f-40a9-9dd0-9eb10028a794', 'admin2',
        '$2a$12$3GjmcPUu7PpwwBreqaD4quYa.ZzHOjHtI7ZFbWV.i9MVwfIUTuusS',
        'admin', 'admin', true, 'avatar1.jpg',
        (SELECT id FROM roles WHERE name = 'ADMIN'));

INSERT INTO users (username, password, first_name, last_name, enabled, avatar, role_id)
VALUES ('thanh2', '$2a$12$3GjmcPUu7PpwwBreqaD4quYa.ZzHOjHtI7ZFbWV.i9MVwfIUTuusS', 'ba', 'thanh', true, 'avatar2.jpg',
        (SELECT id FROM roles WHERE name = 'CONTRIBUTOR'));

INSERT INTO users (username, password, first_name, last_name, enabled, avatar, role_id)
VALUES ('thanh3', '$2a$12$3GjmcPUu7PpwwBreqaD4quYa.ZzHOjHtI7ZFbWV.i9MVwfIUTuusS', 'baaa', 'thanh', true, 'avatar3.jpg',
        (SELECT id FROM roles WHERE name = 'CONTRIBUTOR'));