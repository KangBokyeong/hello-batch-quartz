DELETE FROM users;

INSERT INTO users (name, email, processed, joined_at)
VALUES ('Alice', 'alice@example.com', false, '2025-04-01');

INSERT INTO users (name, email, processed, joined_at)
VALUES ('Bob', 'bob@example.com', false, '2025-04-03');

INSERT INTO users (name, email, processed, joined_at)
VALUES ('Charlie', 'charlie@example.com', false, '2023-12-01');
