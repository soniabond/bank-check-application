create TABLE category(
    id serial primary key,
    name varchar(256)
);

create unique index category_name_uindex on category(name);

create TABLE discharge(
    id bigserial primary key,
    name varchar(256),
    category_name varchar(256) not null,
    foreign key (category_name) references category(name)
);

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

insert into discharge (category_name, name) values ('Косметика', 'EVA');
insert into discharge (category_name, name) values ('Косметика', 'eva');
insert into discharge (category_name, name) values ('Косметика', 'Eva');
insert into discharge (category_name, name) values ('Косметика', 'Косметика');
insert into discharge (category_name, name) values ('Косметика', 'Cosmetics');
insert into discharge (category_name, name) values ('Косметика', 'Brocard');
insert into discharge (category_name, name) values ('Косметика', 'BROCARD');
insert into discharge (category_name, name) values ('Косметика', 'Брокард');
insert into discharge (category_name, name) values ('Косметика', 'Prostor');
insert into discharge (category_name, name) values ('Косметика', 'prostor');
insert into discharge (category_name, name) values ('Косметика', 'Простор');
insert into discharge (category_name, name) values ('Такси', 'Такси');
insert into discharge (category_name, name) values ('Такси', 'UKLON');
insert into discharge (category_name, name) values ('Такси', 'Ontaxi');
insert into discharge (category_name, name) values ('Такси', 'taxi');
insert into discharge (category_name, name) values ('Такси', 'TAXI');
insert into discharge (category_name, name) values ('Накопления', 'Перевод в свою «Копилку»');
insert into discharge (category_name, name) values ('Аптека', 'Аптека');
insert into discharge (category_name, name) values ('Аптека', 'АПТЕКА');
insert into discharge (category_name, name) values ('Доставка еды', 'Raketa');
insert into discharge (category_name, name) values ('Доставка еды', 'Glovo');
insert into discharge (category_name, name) values ('Доставка еды', 'zakaz.ua');
insert into discharge (category_name, name) values ('Переводы', 'карту');

