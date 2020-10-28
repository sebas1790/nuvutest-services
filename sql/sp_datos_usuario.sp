use nuvutest
go

if exists (select * from sysobjects where id = object_id('sp_datos_usuario'))
  drop procedure sp_datos_usuario
go

create proc sp_datos_usuario
(
	@i_username  varchar(32)
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

--Datos generales
SELECT usuario,
		passwd,
		nombre, 
		tarjeta,
		'fecha_reg' = convert(varchar,fecha_reg,103)
FROM usuarios
where usuario = @w_username

return 0
go