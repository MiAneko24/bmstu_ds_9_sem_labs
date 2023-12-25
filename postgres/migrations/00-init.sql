-- file: 00-init.sql
CREATE DATABASE payments;

create schema if not exists payments;
create extension if not exists pgcrypto;

create role payments_master
    nosuperuser
    valid until 'infinity';

grant connect on database payments to payments_master;

grant usage on schema payments to payments_master;
grant select on all tables in schema payments to payments_master;
grant select on all sequences in schema payments to payments_master;
grant execute on all routines in schema payments to payments_master;

alter default privileges in schema payments grant all on tables to payments_master;
alter default privileges in schema payments grant all on functions to payments_master;
alter default privileges in schema payments grant all on routines to payments_master;
alter default privileges in schema payments grant all on types to payments_master;
alter default privileges in schema payments grant all on sequences to payments_master;

alter default privileges in schema payments grant select on tables to payments_master;

create user payment in role payments_master password 'payment';

CREATE TABLE payments.payment
(
    id          SERIAL PRIMARY KEY,
    payment_uid uuid        NOT NULL,
    status      VARCHAR(20) NOT NULL
        CHECK (status IN ('PAID', 'CANCELED')),
    price       INT         NOT NULL
);

CREATE DATABASE reservations;

create schema if not exists reservations;
create extension if not exists pgcrypto;

create role reservations_master
    nosuperuser
    valid until 'infinity';

grant connect on database reservations to reservations_master;

grant usage on schema reservations to reservations_master;
grant select on all tables in schema reservations to reservations_master;
grant select on all sequences in schema reservations to reservations_master;
grant execute on all routines in schema reservations to reservations_master;

alter default privileges in schema reservations grant all on tables to reservations_master;
alter default privileges in schema reservations grant all on functions to reservations_master;
alter default privileges in schema reservations grant all on routines to reservations_master;
alter default privileges in schema reservations grant all on types to reservations_master;
alter default privileges in schema reservations grant all on sequences to reservations_master;
alter default privileges in schema reservations grant select on tables to reservations_master;

create user reservation in role reservations_master password 'reservation';

CREATE TABLE reservations.hotels
(
    id        SERIAL PRIMARY KEY,
    hotel_uid uuid         NOT NULL UNIQUE,
    name      VARCHAR(255) NOT NULL,
    country   VARCHAR(80)  NOT NULL,
    city      VARCHAR(80)  NOT NULL,
    address   VARCHAR(255) NOT NULL,
    stars     INT,
    price     INT          NOT NULL
);

CREATE TABLE reservations.reservation
(
    id              SERIAL PRIMARY KEY,
    reservation_uid uuid UNIQUE NOT NULL,
    username        VARCHAR(80) NOT NULL,
    payment_uid     uuid        NOT NULL,
    hotel_id        INT REFERENCES reservations.hotels (id),
    status          VARCHAR(20) NOT NULL
        CHECK (status IN ('PAID', 'CANCELED')),
    start_date      TIMESTAMP WITH TIME ZONE,
    end_date        TIMESTAMP WITH TIME ZONE
);

INSERT INTO reservations.hotels (id, hotel_uid, name, country, city, address, stars, price) VALUES (1, '049161bb-badd-4fa8-9d90-87c9a82b0668', 'Ararat Park Hyatt Moscow', 'Россия', 'Москва', 'Неглинная ул., 4', 5, 10000);

CREATE DATABASE loyalties;

create schema if not exists loyalties;
create extension if not exists pgcrypto;

create role loyalties_master
    nosuperuser
    valid until 'infinity';

grant connect on database loyalties to loyalties_master;

grant usage on schema loyalties to loyalties_master;
grant select on all tables in schema loyalties to loyalties_master;
grant select on all sequences in schema loyalties to loyalties_master;
grant execute on all routines in schema loyalties to loyalties_master;

alter default privileges in schema loyalties grant all on tables to loyalties_master;
alter default privileges in schema loyalties grant all on functions to loyalties_master;
alter default privileges in schema loyalties grant all on routines to loyalties_master;
alter default privileges in schema loyalties grant all on types to loyalties_master;
alter default privileges in schema loyalties grant all on sequences to loyalties_master;
alter default privileges in schema loyalties grant select on tables to loyalties_master;

create user loyalty in role loyalties_master password 'loyalty';
CREATE TABLE loyalties.loyalty
(
    id                SERIAL PRIMARY KEY,
    username          VARCHAR(80) NOT NULL UNIQUE,
    reservation_count INT         NOT NULL DEFAULT 0,
    status            VARCHAR(80) NOT NULL DEFAULT 'BRONZE'
        CHECK (status IN ('BRONZE', 'SILVER', 'GOLD')),
    discount          INT         NOT NULL
);

INSERT INTO loyalties.loyalty (id, username, reservation_count, status, discount) VALUES (1, 'Test Max', 25, 'GOLD', 10);
