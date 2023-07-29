CREATE SCHEMA IF NOT EXISTS social;

CREATE TABLE IF NOT EXISTS social.user
(
    id       SERIAL PRIMARY KEY,
    created  TIMESTAMP    NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS social_user_username_uidx ON social.user (username);

---

CREATE TABLE IF NOT EXISTS social.post
(
    id      SERIAL PRIMARY KEY,
    created TIMESTAMP    NOT NULL,
    title   VARCHAR(255) NOT NULL,
    text    TEXT         NOT NULL,
    author_id INTEGER      NOT NULL REFERENCES social.user (id)
);

CREATE INDEX IF NOT EXISTS social_post_author_idx ON social.post (author_id);
CREATE INDEX IF NOT EXISTS social_post_created_idx ON social.post (created);

---

CREATE TABLE IF NOT EXISTS social.post_comment
(
    id      SERIAL PRIMARY KEY,
    created TIMESTAMP    NOT NULL,
    text    VARCHAR(255) NOT NULL,
    author_id INTEGER      NOT NULL REFERENCES social.user (id),
    post_id INTEGER      NOT NULL REFERENCES social.post (id)
);

CREATE INDEX IF NOT EXISTS social_post_comment_author_idx ON social.post_comment (author_id);
CREATE INDEX IF NOT EXISTS social_post_comment_post_idx ON social.post_comment (post_id);
CREATE INDEX IF NOT EXISTS social_post_comment_created_idx ON social.post_comment (created);
