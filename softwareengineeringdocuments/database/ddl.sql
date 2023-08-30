create table public.userr
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

alter table public.userr
    owner to root;

create table public.post
(
    post_id   integer default nextval('post_id_seq'::regclass) not null
        primary key,
    uid       uuid
        references public.userr,
    vote      integer,
    timestamp timestamp,
    title     varchar(255),
    content   text
);

alter table public.post
    owner to root;

create table public.comment
(
    id        serial
        primary key,
    uid       uuid
        references public.userr,
    post_id   integer
        references public.post,
    vote      integer default 0,
    timestamp timestamp,
    content   text
);

alter table public.comment
    owner to root;

create table public.chat
(
    id         uuid not null
        primary key,
    created_at timestamp,
    uid        uuid
        references public.userr
);

alter table public.chat
    owner to root;

create table public.messages
(
    chat_id   uuid
        constraint messages_uid_fkey
            references public.chat,
    message   text,
    sender_id uuid
        references public.userr,
    timestamp timestamp,
    mid       bigserial
        primary key,
    role      varchar(255)
);

alter table public.messages
    owner to root;

create table public.categories
(
    id       bigserial
        primary key,
    post_id  integer
        constraint fk_categories_post_id
            references public.post
            on delete cascade,
    category varchar(50)
);

alter table public.categories
    owner to root;

create table public.post_vote
(
    id      bigserial
        primary key,
    post_id integer
        references public.post,
    uid     uuid
        references public.userr,
    cnt     integer default 0
);

alter table public.post_vote
    owner to root;

create table public.comment_vote
(
    id         bigserial
        primary key,
    post_id    integer
        references public.post,
    comment_id integer
        references public.comment,
    uid        uuid
        references public.userr,
    cnt        integer default 0
);

alter table public.comment_vote
    owner to root;

