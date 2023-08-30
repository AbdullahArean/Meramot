CREATE TABLE auth
(
    email   VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255)
);
CREATE TABLE userr
(
    uid      UUID PRIMARY KEY,
    email    VARCHAR(50) UNIQUE references auth (email),
    name     VARCHAR(50),
    role    VARCHAR(50)
);
CREATE TABLE post
(
    post_id        SERIAL PRIMARY KEY,
    uid       UUID REFERENCES userr (uid),
    vote      INTEGER,
    timestamp TIMESTAMP,
    title     VARCHAR(255),
    content   TEXT
);
CREATE TABLE comment
(
    id        SERIAL PRIMARY KEY,
    uid       UUID REFERENCES userr (uid),
    post_id   INTEGER REFERENCES post (id),
    vote      INTEGER,
    timestamp TIMESTAMP,
    content   TEXT
);
CREATE TABLE categories
(
    post_id UUID REFERENCES post (post_id),
    category VARCHAR(50) UNIQUE
);
CREATE TABLE chat
(
    id  UUID PRIMARY KEY,
    created_at TIMESTAMP,
    uid UUID REFERENCES userr (uid)
);
CREATE TABLE messages
(
    chat_id       UUID REFERENCES chat (id),
    message   TEXT,
    sender_id UUID REFERENCES userr (uid),
    timestamp TIMESTAMP
);
