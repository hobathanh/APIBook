INSERT INTO users (id, username, password, first_name, last_name, enabled, avatar, role_id)
VALUES ('6302a41b-e61f-40a9-9dd0-9eb10028a749', 'CRON_JOB',
        '$2a$12$3GjmcPUu7PpwwBreqaD4quYa.ZzHOjHtI7ZFbWV.i9MVwfIUTuusS',
        'CRON', 'JOB', true, 'avatar4.jpg',
        (SELECT id FROM roles WHERE name = 'CONTRIBUTOR'));
