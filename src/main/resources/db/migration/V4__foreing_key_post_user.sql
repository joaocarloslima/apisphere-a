ALTER TABLE posts
ADD COLUMN user_id BIGINT;

ALTER TABLE posts
ADD CONSTRAINT fk_post_user
FOREIGN KEY (user_id) REFERENCES users(id);

UPDATE posts SET user_id = 1 WHERE id = 1;
UPDATE posts SET user_id = 1 WHERE id = 2;
UPDATE posts SET user_id = 2 WHERE id = 3;
UPDATE posts SET user_id = 2 WHERE id = 4;
UPDATE posts SET user_id = 1 WHERE id = 5;