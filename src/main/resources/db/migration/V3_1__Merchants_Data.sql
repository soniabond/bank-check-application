create table merchants(
    id bigserial not null primary key,
    BD_TYPE varchar(10),
    x_token varchar(256),
    merchant_id varchar(256),
    merchant_signature varchar(256),
    card_number varchar(256),
    user_id bigserial not null,
    foreign key (user_id) references users(id)
);

