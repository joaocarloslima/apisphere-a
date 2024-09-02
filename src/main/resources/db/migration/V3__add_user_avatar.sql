ALTER TABLE users
ADD COLUMN avatar VARCHAR(255);

INSERT INTO users (name, bio, email, password, created_at, updated_at, avatar)
VALUES ('João Carlos', 'Software Developer', 'joao@fiap.com.br', '$2a$12$PulShoRE6YPbPsLaKT/gY.LTkQsd/OSA3uq28VOauzznHMckUG8ry', NOW(), NOW(), 'https://avatar.iran.liara.run/public');

INSERT INTO users (name, bio, email, password, created_at, updated_at, avatar)
VALUES ('Maria Silva', 'Project Manager', 'maria@fiap.com.br', '$2a$12$PulShoRE6YPbPsLaKT/gY.LTkQsd/OSA3uq28VOauzznHMckUG8ry', NOW(), NOW(), 'https://avatar.iran.liara.run/public');