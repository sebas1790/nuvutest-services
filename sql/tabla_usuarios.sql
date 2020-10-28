use nuvutest
go

if exists (select * from sysobjects where id = object_id('usuarios'))
  drop table usuarios
go

create table usuarios (
usuario	varchar(12) not null,
passwd	varchar(32) not null,
nombre	varchar(64) not null,
email	varchar(32) null,
tarjeta varchar(20) not null,
fecha_reg datetime not null
)
go

select * from usuarios