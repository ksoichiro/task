insert into `role` values
(1, 'admin', 'Administrator', '2016-01-01 00:00:00', '2016-01-01 00:00:00'),
(2, 'user',  'User',          '2016-01-01 00:00:00', '2016-01-01 00:00:00');

insert into `account` values (1, 'a', 'A', '$2a$10$dvSR/UmH.prOHaDr741LYeXqh9SW1mMY.raA9aUndL4xf03c7Jx3W', 1, 1, '2016-10-01 09:00:00', '2016-10-01 09:00:00');

insert into `task` values
(1,  'Send a mail',    0, 1, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(2,  'Send a mail 2',  0, 1, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(3,  'Send a mail 3',  0, 1, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(4,  'Send a mail 4',  0, 1, null,           '2016-10-02 09:00:00', '2016-10-02 09:00:00');
