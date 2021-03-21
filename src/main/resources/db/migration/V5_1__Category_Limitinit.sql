CREATE table user_category_limit(

    user_id serial not null ,
    category_id integer not null ,
    limit_amount integer not null,
    primary key (user_id, category_id)
);
