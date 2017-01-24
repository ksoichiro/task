insert into `role` values
(1, 'admin', 'Administrator', '2016-01-01 00:00:00', '2016-01-01 00:00:00'),
(2, 'user',  'User',          '2016-01-01 00:00:00', '2016-01-01 00:00:00');

insert into `account` values
(1, 'a', 'A', '$2a$10$dvSR/UmH.prOHaDr741LYeXqh9SW1mMY.raA9aUndL4xf03c7Jx3W', 1, 1, '2016-10-01 09:00:00', '2016-10-01 09:00:00'),
(2, 'b', 'B', '$2a$10$dvSR/UmH.prOHaDr741LYeXqh9SW1mMY.raA9aUndL4xf03c7Jx3W', 1, 1, '2016-10-01 09:00:00', '2016-10-01 09:00:00');

insert into `team` values
(0, '',      '',       '1970-01-01 00:00:00', '1970-01-01 00:00:00'),
(1, 'team1', 'Team 1', '1970-01-01 00:00:00', '1970-01-01 00:00:00'),
(2, 'team2', 'Team 2', '1970-01-01 00:00:00', '1970-01-01 00:00:00'),
(3, 'team3', 'Team 3', '1970-01-01 00:00:00', '1970-01-01 00:00:00');

insert into `team_account` values
(1, 1, 1, '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(2, 2, 1, '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(3, 3, 1, '2016-10-02 09:00:00', '2016-10-02 09:00:00'),
(4, 1, 2, '2016-10-02 09:00:00', '2016-10-02 09:00:00');