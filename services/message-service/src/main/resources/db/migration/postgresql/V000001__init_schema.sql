create sequence hibernate_sequence start 1 increment 1;

    create table messages (
       id int8 not null,
        created_date timestamp not null,
        last_modified_date timestamp not null,
        version int8 not null,
        message varchar(255),
        properties TEXT,
        primary key (id)
    );