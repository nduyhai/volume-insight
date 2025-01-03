create table keyword
(
    id   bigint auto_increment
        primary key,
    name varchar(255) not null
);

create table keyword_search_volume
(
    id               bigint auto_increment
        primary key,
    created_datetime datetime(6) not null,
    keyword_id       bigint      not null,
    search_volume    bigint      not null,
    unique (keyword_id, created_datetime)
);

create table user_subscription
(
    id                bigint auto_increment
        primary key,
    end_datetime      datetime(6)              null,
    keyword_id bigint                   not null,
    start_datetime    datetime(6)              null,
    user_id           varchar(255)             not null,
    subscription_type enum ('DAILY', 'HOURLY') null,
    constraint FK6m0y3384c2v6cnoqpvxie7wl4
        foreign key (keyword_id) references keyword (id)
);

