drop table orderline;
drop table orders;
drop table items;
drop table customers;


create database if not exists ims;
create table if not exists ims.customers(customer_id int auto_increment NOT NULL, first_name varchar(40), surname varchar(40), PRIMARY KEY (customer_id));
create table if not exists ims.items(item_id int auto_increment NOT NULL, item_name varchar(40), item_price double, PRIMARY KEY (item_id));
create table if not exists ims.orders(order_id int auto_increment NOT NULL, customer_id INT NOT NULL, quantity int, PRIMARY KEY (order_id), FOREIGN KEY (customer_id) REFERENCES ims.customers(customer_id));
create table if not exists ims.orderline(orderline_id int auto_increment NOT NULL, item_id int, order_id int, orderline_price double, quantity INT, PRIMARY KEY (orderline_id), foreign key(item_id) references ims.items(item_id), foreign key(order_id) references ims.orders(order_id));


