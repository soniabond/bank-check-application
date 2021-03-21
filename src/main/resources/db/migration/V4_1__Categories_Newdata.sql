create TABLE discharge(
                          id serial primary key,
                          name varchar(256),
                          category_id bigint unsigned not null,
                          foreign key (category_id) references category(id)
);


insert into discharge (category_id, name) values ('1', 'EVA');
insert into discharge (category_id, name) values ('1', 'eva');
insert into discharge (category_id, name) values ('1', 'Eva');
insert into discharge (category_id, name) values ('1', 'Косметика');
insert into discharge (category_id, name) values ('1', 'Cosmetics');
insert into discharge (category_id, name) values ('1', 'Brocard');
insert into discharge (category_id, name) values ('1', 'BROCARD');
insert into discharge (category_id, name) values ('1', 'Брокард');
insert into discharge (category_id, name) values ('1', 'Prostor');
insert into discharge (category_id, name) values ('1', 'prostor');
insert into discharge (category_id, name) values ('1', 'Простор');
insert into discharge (category_id, name) values ('2', 'Такси');
insert into discharge (category_id, name) values ('2', 'UKLON');
insert into discharge (category_id, name) values ('2', 'Ontaxi');
insert into discharge (category_id, name) values ('2', 'taxi');
insert into discharge (category_id, name) values ('2', 'TAXI');
insert into discharge (category_id, name) values ('3', 'Перевод в свою «Копилку»');
insert into discharge (category_id, name) values ('4', 'Аптека');
insert into discharge (category_id, name) values ('4', 'АПТЕКА');
insert into discharge (category_id, name) values ('8', 'Raketa');
insert into discharge (category_id, name) values ('8', 'Glovo');
insert into discharge (category_id, name) values ('8', 'zakaz.ua');
insert into discharge (category_id, name) values ('10', 'карту');