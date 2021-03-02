CREATE table user_category_limit(

    user_id serial not null ,
    category_id serial not null ,
    limit_amount serial not null,
    primary key (user_id, category_id)
);
