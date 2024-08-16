drop table if exists message_media;
alter table messages
add column path varchar(255);
