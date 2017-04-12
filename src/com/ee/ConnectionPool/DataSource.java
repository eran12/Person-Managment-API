package com.ee.ConnectionPool;

import java.sql.SQLException;

import java.sql.Connection;

public class DataSource {

	static JdbcConnectionPool pool = new JdbcConnectionPool();
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		Connection connection = pool.getConnectionFromPool();
		return connection;
	}

	public static void returnConnection(Connection connection) {
		pool.returnConnectionToPool(connection);
	}
}