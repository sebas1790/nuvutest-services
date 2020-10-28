package com.nuvutest.springboot.web.app.controllers;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlConnection {
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			String dbURL = "jdbc:sqlserver://104.197.36.65:1433";
            String user = "sqlserver";
            String pass = "adminsa";
            conn = DriverManager.getConnection(dbURL, user, pass);
			if (conn != null) {
			    System.out.println("Connected");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return conn;
	}
}
