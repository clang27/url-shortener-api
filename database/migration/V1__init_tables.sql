CREATE SCHEMA IF NOT EXISTS url;
GRANT ALL ON SCHEMA url TO test_user;

CREATE TABLE url.slugs (
    id          SERIAL          PRIMARY KEY,
    slug        VARCHAR(16)     UNIQUE NOT NULL,
    target      VARCHAR(1024)    UNIQUE NOT NULL,
    created     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE url.redirect_events (
    id          SERIAL          PRIMARY KEY,
    slug_id     INTEGER         NOT NULL,
    user_agent  VARCHAR(256)    NOT NULL,
    created     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (slug_id)       REFERENCES url.slugs (id)
);
