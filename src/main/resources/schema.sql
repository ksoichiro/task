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
    `account_id` int not null,
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
    `team_id` int,
    `account_id` int,
    `name` varchar(128) not null,
    `created_at` datetime(3),
    `updated_at` datetime(3)
);

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
