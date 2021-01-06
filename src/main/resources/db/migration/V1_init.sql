create table users(
                      id bigserial primary key,
                      email varchar(255) not null ,
                      first_name varchar(255) not null ,
                      password text,
                      created_at timestamp not null default now()
);

create unique index users_email_uindex on users(email);

create table authorities(
    id serial primary key ,
    value varchar(255) not null
);

create unique index authorities_value_uindex on authorities(value);

create table user_authorities(
    user_id bigint not null ,
    authority_id int not null ,
    primary key (user_id, authority_id),
    constraint user_authorities_users_fk foreign key (user_id) references users(id),
    constraint user_authorities_authorities_fk foreign key (authority_id) references authorities(id)

);


insert into authorities values ('ROLE_USER');
insert into authorities values ('ROLE_ADMIN');
