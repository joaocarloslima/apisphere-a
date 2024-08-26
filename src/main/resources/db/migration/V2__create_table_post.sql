CREATE TABLE IF NOT EXISTS posts (
   id BIGINT AUTO_INCREMENT,
   text VARCHAR(255),
   created_at datetime,
    PRIMARY KEY (id)
);

INSERT INTO posts (text, created_at) VALUES ('Hello World', '2024-08-26 10:00:00');
INSERT INTO posts (text, created_at) VALUES ('My second post', '2024-08-26 11:00:00');
INSERT INTO posts (text, created_at) VALUES ('Learning SQL with H2', '2024-08-26 12:00:00');
INSERT INTO posts (text, created_at) VALUES ('Another example post', '2024-08-26 13:00:00');
INSERT INTO posts (text, created_at) VALUES ('Final example for today', '2024-08-26 14:00:00');