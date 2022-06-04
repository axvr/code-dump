DROP TABLE IF EXISTS users;

CREATE TABLE users
(id             INTEGER PRIMARY KEY,
 username       TEXT UNIQUE,
 email_address  TEXT,
 password_hash  BLOB,
 created_at     DATETIME,
 is_active      BOOLEAN DEFAULT(0));
