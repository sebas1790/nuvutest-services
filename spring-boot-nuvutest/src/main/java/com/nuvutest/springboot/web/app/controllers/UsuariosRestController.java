package com.nuvutest.springboot.web.app.controllers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Types;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsuariosRestController {

	@GetMapping("/cargar")
	public HashMap<String, Object> cargar(@RequestParam String username) throws Exception {
		CallableStatement cs = null;
		Connection conn = SqlConnection.getConnection();
		HashMap<String, Object> rtn = new LinkedHashMap<String, Object>();
		rtn.put("name", username);
		//List<HashMap<String, Object>> list = new ArrayList();
		try {

			if (conn != null) {
				cs = conn.prepareCall("{ ? = call nuvutest..sp_datos_usuario(?) }");
				cs.setEscapeProcessing(true);
				cs.setQueryTimeout(90);
				cs.registerOutParameter(1, Types.INTEGER);

				cs.setString(2, username);

				boolean isRs = cs.execute();
				
				//do {
					ResultSet rs = cs.getResultSet();
					ResultSetMetaData rsmd = rs.getMetaData();
					while (rs.next()) {						
						int columnsNumber = rsmd.getColumnCount();
						//rtn = new LinkedHashMap<String, Object>();
						for(int i=1;i<=columnsNumber;i++) {
							rtn.put(rsmd.getColumnName(i), rs.getObject(i));
						}
					//	list.add(rtn);
					}
				//} while (cs.getMoreResults());
				
				int retCode = cs.getInt(1);
				SQLWarning statementWarning = cs.getWarnings();
				//rtn = new LinkedHashMap<String, Object>();

				if (retCode == 0) {
					System.out.println("Ejecución exitosa!");
					rtn.put("code", retCode);
					rtn.put("mensaje", "Usuario existe");
				} else {
					rtn.put("code", retCode);
					rtn.put("mensaje", "Usuario no existe");
				}

				while (statementWarning != null) {
					System.out.println("Mensaje del SP: " + statementWarning.getMessage());
					statementWarning = statementWarning.getNextWarning();
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			rtn = new LinkedHashMap<String, Object>();
			rtn.put("code", 94865);
			rtn.put("mensaje", e.getMessage());
		} finally {
			if (conn != null) conn.close();
		}
		return rtn;
	}

	@GetMapping("/register")
	public HashMap<String, Object> register(@RequestParam String username, @RequestParam String password,
			@RequestParam String nombre, @RequestParam String tarjeta) throws Exception {
		System.out.println(username);
		CallableStatement cs = null;
		Connection conn = SqlConnection.getConnection();
		HashMap<String, Object> rtn = new LinkedHashMap<String, Object>();

		rtn.put("code", 00);
		rtn.put("name", username);
		try {

			if (conn != null) {
				cs = conn.prepareCall("{ ? = call nuvutest..sp_registrar_usuario(?,?,?,?) }");
				cs.setEscapeProcessing(true);
				cs.setQueryTimeout(90);
				cs.registerOutParameter(1, Types.INTEGER);

				cs.setString(2, username);
				cs.setString(3, password);
				cs.setString(4, nombre);
				cs.setString(5, tarjeta);

				boolean isRs = cs.execute();
				int retCode = cs.getInt(1);
				SQLWarning statementWarning = cs.getWarnings();

				if (retCode == 0) {
					System.out.println("Ejecución exitosa!");
					rtn.put("mensaje", "Registrado correctamente.");
				} else {
					System.out.println("Ejecución error!");
				}

				while (statementWarning != null) {
					System.out.println("Mensaje del SP: " + statementWarning.getMessage());
					statementWarning = statementWarning.getNextWarning();
				}

				if (isRs) {
					ResultSet rs = cs.getResultSet();
					if (rs.next()) {
						String ex = rs.getString(1);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			rtn.remove("code");
			rtn.put("code", 1);
			rtn.put("mensaje", e.getMessage());
		} finally {
			if (conn != null) conn.close();
		}
		return rtn;
	}

	@GetMapping("/login")
	public HashMap<String, Object> login(@RequestParam String name, @RequestParam String password,
			@RequestParam String ip) throws SQLException {
		CallableStatement cs = null;
		Connection conn = SqlConnection.getConnection();
		HashMap<String, Object> rtn = new LinkedHashMap<String, Object>();

		rtn.put("code", 00);
		rtn.put("name", name);
		try {

			if (conn != null) {
				cs = conn.prepareCall("{ ? = call zonakiller..sp_login(?,?,?) }");
				cs.setEscapeProcessing(true);
				cs.setQueryTimeout(90);
				cs.registerOutParameter(1, Types.INTEGER);

				cs.setString(2, name);
				cs.setString(3, password);

				boolean isRs = cs.execute();
				int retCode = cs.getInt(1);
				SQLWarning statementWarning = cs.getWarnings();

				if (retCode == 0) {
					System.out.println("Autenticacion exitosa!");
					rtn.put("mensaje", "Autenticacion correcta.");
				} else {
					System.out.println("Autenticacion error!");
				}

				while (statementWarning != null) {
					System.out.println("Mensaje del SP: " + statementWarning.getMessage());
					statementWarning = statementWarning.getNextWarning();
				}

				if (isRs) {
					ResultSet rs = cs.getResultSet();
					if (rs.next()) {
						String ex = rs.getString(1);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			rtn.remove("code");
			rtn.put("code", 1);
			rtn.put("mensaje", e.getMessage());
		} finally {
			if (conn != null) conn.close();
		}
		return rtn;
	}

	@GetMapping("/update")
	public HashMap<String, Object> update(@RequestParam String username, @RequestParam(required = false) String password,
			@RequestParam(required = false) String nombre, @RequestParam(required = false) String tarjeta) throws SQLException {
		CallableStatement cs = null;
		Connection conn = SqlConnection.getConnection();
		HashMap<String, Object> rtn = new LinkedHashMap<String, Object>();
		rtn.put("name", username);
		rtn.put("code", 00);

		try {

			if (conn != null) {
				cs = conn.prepareCall(
						"{ ? = call nuvutest..sp_update_usuario(?,?,?,?) }");
				cs.setEscapeProcessing(true);
				cs.setQueryTimeout(90);
				cs.registerOutParameter(1, Types.INTEGER);

				cs.setObject(2, username);
				cs.setObject(3, password);
				cs.setObject(4, nombre);
				cs.setObject(5, tarjeta);

				boolean isRs = cs.execute();
				int retCode = cs.getInt(1);
				SQLWarning statementWarning = cs.getWarnings();

				if (retCode == 0) {
					System.out.println("Ejecución exitosa!");
					rtn.put("mensaje", "Actiualizado correctamente.");
				} else {
					System.out.println("Ejecución error!");
				}

				while (statementWarning != null) {
					System.out.println("Mensaje del SP: " + statementWarning.getMessage());
					statementWarning = statementWarning.getNextWarning();
				}

				if (isRs) {
					ResultSet rs = cs.getResultSet();
					if (rs.next()) {
						String ex = rs.getString(1);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			rtn.remove("code");
			rtn.put("code", 1);
			rtn.put("mensaje", e.getMessage());
		} finally {
			if (conn != null) conn.close();
		}

		return rtn;
	}

}
