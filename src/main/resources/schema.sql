-- for non-embedded database
drop table if exists `account`;
drop table if exists `task`;
drop table if exists `memo`;
drop table if exists `tag`;
drop table if exists `task_tag`;
drop table if exists `team`;
drop table if exists `team_account`;
drop table if exists `role`;
drop table if exists `permission`;
drop table if exists `role_permission`;
drop table if exists `project`;

create table `account` (
    `id` int primary key auto_increment,
    `username` varchar(255) not null,
    `name` varchar(255) not null,
    `password` varchar(255) not null,
    `enabled` tinyint(1) not null default 1,
    `role_id` int not null,
    `created_at` datetime(3),
    `updated_at` datetime(3)
);

create table `task` (
    `id` int primary key auto_increment,
    `name` varchar(255) not null,
    `status` int not null default 0,
    `account_id` int not null,
    `parent_task_id` int,
    `scheduled_at` date,
    `created_at` datetime(3),
    `updated_at` datetime(3)
);

create table `memo` (
    `id` int primary key auto_increment,
    `memo` text not null,
    `created_at` datetime(3),
    `updated_at` datetime(3)
);

create table `tag` (
    `id` int primary key auto_increment,
    `team_id` int not null default 0,
    `account_id` int not null default 0,
    `name` varchar(128) not null,
    `created_at` datetime(3),
    `updated_at` datetime(3)
);
alter table `tag`
    add unique `uq_tag` (`team_id`, `account_id`, `name`);

create table `task_tag` (
    `id` int primary key auto_increment,
    `task_id` int not null,
    `tag_id` int not null,
    `created_at` datetime(3),
    `updated_at` datetime(3)
);

create table `team` (
    `id` int primary key auto_increment,
    `cd` varchar(255) not null,
    `name` varchar(255) not null,
    `created_at` datetime(3),
    `updated_at` datetime(3)
);

create table `team_account` (
    `id` int primary key auto_increment,
    `team_id` int not null,
    `account_id` int not null,
    `created_at` datetime(3),
    `updated_at` datetime(3)
);

create table `role` (
    `id` int primary key auto_increment,
    `cd` varchar(255) not null,
    `name` varchar(255) not null,
    `created_at` datetime(3),
    `updated_at` datetime(3)
);

create table `permission` (
    `id` int primary key auto_increment,
    `cd` varchar(255) not null,
    `name` varchar(255) not null,
    `created_at` datetime(3),
    `updated_at` datetime(3)
);

create table `role_permission` (
    `id` int primary key auto_increment,
    `role_id` int not null,
    `permission_id` int not null,
    `created_at` datetime(3),
    `updated_at` datetime(3)
);

create table `project` (
    `id` int primary key auto_increment,
    `team_id` int not null default 0,
    `cd` varchar(255) not null,
    `name` varchar(255) not null,
    `created_at` datetime(3),
    `updated_at` datetime(3)
);
