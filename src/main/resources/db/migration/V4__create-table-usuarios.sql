create table usuarios (
     id bigint not null auto_increment,
     login varchar(300) not null,
     nombre varchar(100) not null,

     primary key (id)
);