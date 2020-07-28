create sequence hibernate_sequence start with 1 increment by 1;

create table child (
       id bigint not null,
        hobby varchar(255) not null,
        parent_id bigint not null,
        primary key (id)
    );

create table parent (
       id bigint not null,
        job varchar(255) not null,
        primary key (id)
    );

alter table child
       add constraint FK_from_child_to_parent
       foreign key (parent_id)
       references parent;

