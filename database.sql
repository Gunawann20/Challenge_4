CREATE TABLE users
(
    id Bigserial primary key ,
    username varchar(100) not null ,
    email varchar(100) unique not null ,
    password varchar(100) not null
);
select * from users;

CREATE TABLE admins
(
    id Bigserial primary key ,
    user_id Bigint not null
);

select * from admins;

CREATE TABLE merchants
(
  id Bigserial primary key ,
  admin_id Bigint not null ,
  name varchar(100) not null ,
  location varchar(100) not null ,
  open boolean not null default false,
  CONSTRAINT fk_merchants_admins
  FOREIGN KEY (admin_id) REFERENCES admins(id)
);

SELECT * from merchants;

CREATE table products
(
  id Bigserial primary key ,
  merchant_id Bigint not null ,
  name varchar(100) not null ,
  price int not null ,
  CONSTRAINT fk_products_merchants
  FOREIGN KEY (merchant_id) REFERENCES merchants(id)
);

select * from products;

create table orders
(
    id Bigserial primary key ,
    user_id Bigint not null ,
    time timestamp not null ,
    destination varchar(100) not null ,
    completed boolean not null default false,
    CONSTRAINT fk_orders_users
    FOREIGN KEY (user_id) REFERENCES users(id)
);

select * from orders;

create table order_details
(
    id Bigserial primary key ,
    order_id Bigint not null ,
    product_id Bigint not null ,
    quantity int not null ,
    total_price Bigint not null ,
    CONSTRAINT fk_order_details_and_orders FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_order_details_and_products FOREIGN KEY (product_id) REFERENCES products(id)
);

select * from order_details;