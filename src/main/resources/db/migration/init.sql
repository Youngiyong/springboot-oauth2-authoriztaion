create table users
(
    id         INT  not null
        primary key AUTO_INCREMENT,
    username       varchar(100) not null unique ,
    email      varchar(50) not null unique,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL
) ENGINE = InnoDb;

create table roles
(
    id         INT  not null
        primary key AUTO_INCREMENT,
    name       varchar(100) not null unique
) ENGINE = InnoDb;

create table user_roles
(
    user_id int not null,
    role_id int not null
) ENGINE = InnoDb;

INSERT INTO roles(id, name) VALUES (1, 'USER');
INSERT INTO roles(id, name) VALUES (2, 'MANAGER');
INSERT INTO roles(id, name) VALUES (3, 'ADMIN');

INSERT INTO users(id, email, username, password)
VALUES (1, 'youn9354@naver.com', 'youn9354', '$2a$10$3MUolsky4nfLXHOgP3EHS.zRN4OM/hbgvnihP.VJdnwpdBqpbv3mC');

INSERT INTO users(id, email, username, password)
VALUES (2, 'admin@example.com', 'giyong0503', '$2a$10$6Hn2bAmJ2BXJUsq8JkThV.3qqArTMig9DFDjEQUGIcYUuEoYQWnKq');

INSERT INTO user_roles(user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles(user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles(user_id, role_id) VALUES (2, 1);
INSERT INTO user_roles(user_id, role_id) VALUES (2, 3);

