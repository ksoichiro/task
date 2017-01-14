insert into `role` values (1, 'admin', 'Administrator', '2016-01-01 00:00:00', '2016-01-01 00:00:00');

insert into `account` values (1, 'a', 'A', 'PASSWORD', 1, 1, '2016-10-01 09:00:00', '2016-10-01 09:00:00');

insert into `task` values
(1, 'Send a mail1', 0, 1, null, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(2, 'Send a mail2', 1, 1, null, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(3, 'Send a mail3', 3, 1, null, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(4, 'Send a mail4', 2, 1, null, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(5, 'Send a mail5', 4, 1, null, current_date(), '2016-10-02 09:00:00', '2016-10-02 09:00:00');
