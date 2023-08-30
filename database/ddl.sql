-- we don't know how to generate root <with-no-name> (class Root) :(

comment on database postgres is 'default administrative connection database';

create sequence post_id_seq;

alter sequence post_id_seq owner to postgres;

create table userr
(
    uid         uuid not null
        primary key,
    email       varchar(50)
        unique,
    name        varchar(50),
    role        varchar(50),
    password    varchar(255),
    profile_pic text
);

alter table userr
    owner to postgres;

create table post
(
    post_id   integer default nextval('post_id_seq'::regclass) not null
        primary key,
    uid       uuid
        references userr,
    vote      integer,
    timestamp timestamp,
    title     varchar(255),
    content   text
);

alter table post
    owner to postgres;

create table comment
(
    id        serial
        primary key,
    uid       uuid
        references userr,
    post_id   integer
        references post,
    vote      integer default 0,
    timestamp timestamp,
    content   text
);

alter table comment
    owner to postgres;

create table chat
(
    id         uuid not null
        primary key,
    created_at timestamp,
    uid        uuid
        references userr
);

alter table chat
    owner to postgres;

create table messages
(
    chat_id   uuid
        constraint messages_uid_fkey
            references chat,
    message   text,
    sender_id uuid
        references userr,
    timestamp timestamp,
    mid       bigserial
        primary key,
    role      varchar(255)
);

alter table messages
    owner to postgres;

create table categories
(
    id       bigserial
        primary key,
    post_id  integer
        constraint fk_categories_post_id
            references post
            on delete cascade,
    category varchar(50)
);

alter table categories
    owner to postgres;

create table post_vote
(
    id      bigserial
        primary key,
    post_id integer
        references post,
    uid     uuid
        references userr,
    cnt     integer default 0
);

alter table post_vote
    owner to postgres;

create table comment_vote
(
    id         bigserial
        primary key,
    post_id    integer
        references post,
    comment_id integer
        references comment,
    uid        uuid
        references userr,
    cnt        integer default 0
);

alter table comment_vote
    owner to postgres;

