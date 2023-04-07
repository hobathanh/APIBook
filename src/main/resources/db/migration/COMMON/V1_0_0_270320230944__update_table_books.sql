ALTER TABLE books
    ADD COLUMN subtitle VARCHAR(255) NOT NULL DEFAULT '',
    ADD COLUMN publisher VARCHAR(255) NOT NULL DEFAULT '',
    ADD COLUMN isbn13 VARCHAR(13),
    ADD COLUMN price VARCHAR(20),
    ADD COLUMN year Integer CHECK (year >= 0),
    ADD COLUMN rating NUMERIC(2,1) DEFAULT 0 CHECK (rating >= 0 AND rating <= 5);

ALTER TABLE books
    ADD CONSTRAINT unique_isbn13 UNIQUE (isbn13);

ALTER TABLE books
ALTER
COLUMN description TYPE TEXT;
