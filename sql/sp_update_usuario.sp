use nuvutest
go

if exists (select * from sysobjects where id = object_id('sp_update_usuario'))
  drop procedure sp_update_usuario
go

create proc sp_update_usuario
(
	@i_username  varchar(12),
	@i_password  varchar(32) = null,
	@i_nombre    varchar(64) = null,
	@i_tarjeta   varchar(20) = null,
	@i_email     varchar(32)  = null
)
as
declare
	@w_username  varchar(32),
	@w_user_id   int,
	@w_msg		 varchar(64)

select @w_username = LOWER(LTRIM(RTRIM(REPLACE(@i_username, char(9), ''))))

if not exists (select 1 from usuarios where usuario = @w_username)
begin
	select @w_msg = 'sp_update_usuario: El usuario (' + @w_username + ') no existe'
	RAISERROR(@w_msg,11,1)
	return 1
end

UPDATE usuarios
	SET passwd		= isnull(@i_password        ,passwd	   )
	   ,nombre        = isnull(@i_nombre        ,nombre     )
	   ,tarjeta        = isnull(@i_tarjeta        ,tarjeta     )
	   ,email       = isnull(@i_email       ,email    )
where usuario = @w_username

if @@ERROR != 0
begin
	RAISERROR('sp_update_usuario: Error al actualizar el usuario',11,1)
	return 1
end

return 0
go
