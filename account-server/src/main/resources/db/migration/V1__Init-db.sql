drop table if exists account cascade;
drop table if exists transaction cascade;

create table account (id SERIAL not null, balance int8, primary key (id));
create table transaction (id SERIAL not null, time timestamp, value int8, account_id int4, primary key (id));

alter table transaction add constraint FK__transaction_account foreign key (account_id) references account;