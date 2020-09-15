create database java30_blogdemo;
use java30_blogdemo;

drop table if exists user;
create table user (
    userId int primary key auto_increment,
    name varchar(50) not null,
    password varchar(50) not null
);

drop table if exists article;
create table article (
    id int primary key auto_increment,
    title varchar(255) not null,
    content text not null,
    userId int not null,
    foreign key(userId) references user(userId)
);