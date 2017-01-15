insert into `role` values
(1, 'admin', 'Administrator', '2016-01-01 00:00:00', '2016-01-01 00:00:00'),
(2, 'user',  'User',          '2016-01-01 00:00:00', '2016-01-01 00:00:00');

insert into `account` values (1, 'a', 'A', '$2a$10$dvSR/UmH.prOHaDr741LYeXqh9SW1mMY.raA9aUndL4xf03c7Jx3W', 1, 1, '2016-10-01 09:00:00', '2016-10-01 09:00:00');

insert into `task` values
(1,  'Send a mail',    0, 1, null, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(2,  'Send a mail 2',  0, 1, null, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(3,  'Send a mail 3',  0, 1, null, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(4,  'Send a mail 4',  0, 1, null, null,           '2016-10-02 09:00:00', '2016-10-02 09:00:00');

insert into `tag` values
(0, 0, 0, '',         '1970-01-01 00:00:00', '1970-01-01 00:00:00'),
(1, 0, 1, 'Work',     '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(2, 0, 1, 'Private',  '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(3, 0, 1, 'Home',     '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(4, 0, 1, 'Hobby',    '2016-10-02 09:00:00', '2016-10-02 09:00:00');

insert into `team` values (0, '', '', '1970-01-01 00:00:00', '1970-01-01 00:00:00');