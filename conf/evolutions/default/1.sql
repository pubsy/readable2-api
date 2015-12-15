# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table books (
  id                        bigint not null,
  inserted_at               timestamp not null,
  external_id               varchar(255) not null,
  title                     varchar(255) not null,
  description               TEXT not null,
  author_name               varchar(255) not null,
  thumbnail_url             TEXT not null,
  small_thumbnail_url       TEXT not null,
  constraint pk_books primary key (id))
;

create table users (
  id                        bigint not null,
  inserted_at               timestamp not null,
  username                  varchar(255) not null,
  constraint uq_users_username unique (username),
  constraint pk_users primary key (id))
;

create table users_books (
  id                        bigint not null,
  inserted_at               timestamp not null,
  user_id                   bigint not null,
  book_id                   bigint not null,
  type                      varchar(16),
  constraint ck_users_books_type check (type in ('READ','PLANNING_TO_READ')),
  constraint uq_users_books_1 unique (user_id,book_id),
  constraint pk_users_books primary key (id))
;

create sequence books_seq;

create sequence users_seq;

create sequence users_books_seq;

alter table users_books add constraint fk_users_books_user_1 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_users_books_user_1 on users_books (user_id);
alter table users_books add constraint fk_users_books_book_2 foreign key (book_id) references books (id) on delete restrict on update restrict;
create index ix_users_books_book_2 on users_books (book_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists books;

drop table if exists users;

drop table if exists users_books;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists books_seq;

drop sequence if exists users_seq;

drop sequence if exists users_books_seq;

