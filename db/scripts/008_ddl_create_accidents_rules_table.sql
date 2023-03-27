create table accidents_rules
(
    id serial primary key,
    accident_id int references accidents(id) NOT NULL,
    rule_id int references rules(id) NOT NULL
);
