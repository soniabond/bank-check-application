create TABLE category(
    id serial primary key,
    name varchar(256)
);

create unique index category_name_uindex on category(name);


insert into category (name) values ('Косметика');
insert into category (name) values ('Такси');
insert into category (name) values ('Накопления');
insert into category (name) values ('Аптека');
insert into category (name) values ('Рестораны');
insert into category (name) values ('Поступления');
insert into category (name) values ('Зарплата');
insert into category (name) values ('Доставка еды');
insert into category (name) values ('Почта');
insert into category (name) values ('Переводы');
insert into category (name) values ('Другое');
insert into category (name) values ('Одежда и обувь');
insert into category (name) values ('Продукты');
insert into category (name) values ('Техника');
insert into category (name) values ('Книги');
insert into category (name) values ('Подписки');


