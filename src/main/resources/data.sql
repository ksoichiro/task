insert into `role` values
(1, 'admin', 'Administrator', '2016-01-01 00:00:00', '2016-01-01 00:00:00'),
(2, 'user',  'User',          '2016-01-01 00:00:00', '2016-01-01 00:00:00');

-- password is 'test'
insert into `account` values (1, 'test1', 'Test user 1', '$2a$10$dvSR/UmH.prOHaDr741LYeXqh9SW1mMY.raA9aUndL4xf03c7Jx3W', 1, 1, '2016-10-01 09:00:00', '2016-10-01 09:00:00');
