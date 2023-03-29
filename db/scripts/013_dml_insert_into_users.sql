insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$aZELAYERQf4Jq0t1krUVs.DNJ7gZ4vdb.knfpQbdsET9mdpGfGxnG',
(select id from authorities where authority = 'ROLE_ADMIN'));
