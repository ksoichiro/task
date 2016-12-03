set sql_mode=NO_AUTO_VALUE_ON_ZERO;

truncate table `role`;
truncate table `account`;
truncate table `task`;
truncate table `tag`;
truncate table `team`;

insert into `role` values
(1, 'admin', 'Administrator', '2016-01-01 00:00:00', '2016-01-01 00:00:00'),
(2, 'user',  'User',          '2016-01-01 00:00:00', '2016-01-01 00:00:00');

-- password is 'test'
insert into `account` values (1, 'test1', 'Test user 1', '$2a$10$dvSR/UmH.prOHaDr741LYeXqh9SW1mMY.raA9aUndL4xf03c7Jx3W', 1, 1, '2016-10-01 09:00:00', '2016-10-01 09:00:00');

insert into `task` values
(1,  'Send a mail',    0, 1, null, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(2,  'Send a mail 2',  0, 1, null, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(3,  'Send a mail 3',  0, 1, null, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(4,  'Send a mail 4',  0, 1, null, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(5,  'Send a mail 5',  0, 1, null, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(6,  'Send a mail 6',  0, 1, null, null,           '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(7,  'Send a mail 7',  0, 1, null, null,           '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(8,  'Send a mail 8',  0, 1, null, null,           '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(9,  'Send a mail 9',  0, 1, null, null,           '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(10, 'Send a mail 10', 0, 1, null, null,           '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(11, 'Send a mail 11', 0, 1, null, null,           '2016-10-02 09:00:00', '2016-10-02 09:00:00');

insert into `tag` values
(0, 0, 0, '',         '1970-01-01 00:00:00', '1970-01-01 00:00:00'),
(1, 0, 1, 'Work',     '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(2, 0, 1, 'Routine',  '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(3, 0, 1, 'Study',    '2016-10-02 09:00:00', '2016-10-02 09:00:00');

insert into `team` values (0, '', '', '1970-01-01 00:00:00', '1970-01-01 00:00:00');
