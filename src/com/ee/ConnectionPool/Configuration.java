package com.ee.ConnectionPool;

public class Configuration {

	public String DB_USER_NAME = "root";
	public String DB_PASSWORD = "root";
	public String DB_URL = "jdbc:mysql://127.0.0.1:3306/person_managment?useSSL=false&autoReconnect=true";
	public String DB_DRIVER = "com.mysql.jdbc.Driver";
	public Integer DB_MAX_CONNECTIONS = 5;

	private Configuration() { }

	private static Configuration configuration = new Configuration();

	public static Configuration getInstance() {
		return configuration;
	}
}
