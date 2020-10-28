package com.nuvutest.springboot.web.app.models;

public class Usuario {
	
	private String user;
	private String password;
	private String token;
	private String nombre;
	private String email;
	private String tarjeta;
	
	public Usuario() {
	}
	
	public Usuario(String nombre, String tarjeta, String email) {
		this.nombre = nombre;
		this.email = email;
		this.tarjeta = tarjeta;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}
	
	
	
}
