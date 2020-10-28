use nuvutest
go

if exists (select * from sysobjects where id = object_id('sp_registrar_usuario'))
  drop procedure sp_registrar_usuario
go

create proc sp_registrar_usuario
(
	@i_username  varchar(12),
	@i_password  varchar(32),
	@i_nombre    varchar(64),
	@i_tarjeta   varchar(20),
	@i_email     varchar(32)  = null
)
as
declare
	@w_username  varchar(32),
	@w_user_id   int,
	@w_msg		 varchar(64)

select @w_username = LOWER(LTRIM(RTRIM(REPLACE(@i_username, char(9), ''))))

if exists (select 1 from usuarios where usuario = @w_username)
begin
	select @w_msg = 'sp_registrar_usuario: El usuario (' + @w_username + ') ya existe'
	RAISERROR(@w_msg,11,1)
	return 1
end

begin tran

INSERT INTO usuarios
           (usuario,
			passwd,
			nombre, 
			tarjeta,
			fecha_reg)
     VALUES
           (@w_username
           ,@i_password
           ,@i_nombre
		   ,@i_tarjeta
		   ,getdate())

if @@ERROR != 0
begin
	rollback tran
	RAISERROR('sp_registrar_usuario: Error al registrar el usuario',11,1)
	return 1
end

commit tran

return 0
go
