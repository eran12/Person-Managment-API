package com.ee.DBDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.ee.Bean.User;
import com.ee.ConnectionPool.DataSource;
import com.ee.Dao.UserDao;

public class userDbDao implements UserDao {

	@Override
	public Collection<User> getAllUsers() {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		String sqlString = "SELECT * FROM person_managment.users ORDER BY id";

		Collection<User> allUsers = new ArrayList<>();

		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			result = statement.executeQuery(sqlString);

			while (result.next()) {
				User user = new User(result.getString("name"), result.getString("address"), result.getInt("phone"));
				user.setId(result.getLong("id"));
				allUsers.add(user);
				user = null;
			}

		} catch (ClassNotFoundException | SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DataSource.returnConnection(connection);
		}

		return allUsers;
	}

	@Override
	public User upDateUser(User user) throws NullPointerException {

		if (user == null) {
			throw new NullPointerException("Can't udpate null user");
		}

		Connection connection = null;
		PreparedStatement prepared = null;
		User updatedUser = null;
		String sqlString = "UPDATE person_managment.users SET name=?, address=?, phone=? WHERE id=?";

		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			prepared = connection.prepareStatement(sqlString);
			prepared.setString(1, user.getName());
			prepared.setString(2, user.getAddress());
			prepared.setLong(3, user.getPhone());
			prepared.setLong(4, user.getId());
			prepared.executeUpdate();
			connection.commit();

			updatedUser = this.getUserById(user.getId());

		} catch (ClassNotFoundException | SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DataSource.returnConnection(connection);
		}
		return updatedUser;
	}

	@Override
	public User addNewUser(User user) throws NullPointerException{
		
		if (user == null) {
			throw new NullPointerException("Can't udpate null user");
		}
		
		Connection connection = null;
		PreparedStatement prepared = null;
		String sqlString = "INSERT INTO person_managment.users (name,adress,phone) VALUES (?,?,?)";

		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			prepared = connection.prepareStatement(sqlString);
			prepared.setString(1, user.getName());
			prepared.setString(2, user.getAddress());
			prepared.setLong(3, user.getPhone());
			prepared.executeUpdate();
			connection.commit();

		} catch (ClassNotFoundException | SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DataSource.returnConnection(connection);
		}
		
		return this.getUserByName(user.getName());
	}

	@Override
	public String deleteUser(User user) throws NullPointerException{

		if (user == null) {
			throw new NullPointerException("Can't udpate null user");
		}
		
		Connection connection = null;
		PreparedStatement statement = null;
		String sqlString = "DELETE FROM person_managment.users WHERE ID=?";

		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sqlString);
			statement.setLong(1, user.getId());
			statement.executeUpdate();
			connection.commit();

		} catch (ClassNotFoundException | SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				return "Fail to delete user: " + user.toString();
			}
			e.printStackTrace();
		}

		finally {
			DataSource.returnConnection(connection);
		}
		return "Success to remove user: " + user.toString();
	}

	@Override
	public User getUserById(long id) throws Exception {
		
		if (id <= 0) {
			throw new Exception("Id can`t be less then zero");
		}
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		User user = null;
		String sqlString = "SELECT * FROM person_managment.users WHERE id=?";

		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sqlString);
			statement.setLong(1, id);
			connection.commit();
			result = statement.executeQuery();

			while (result.next()) {
				user = new User(result.getString("name"), result.getString("address"), result.getLong("phone"));
				user.setId(result.getLong("id"));
			}

		} catch (ClassNotFoundException | SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			DataSource.returnConnection(connection);
		}
		return user;
	}

	@Override
	public User getUserByName(String name) {

		if (name == null) {
			return null;
		}

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		User user = null;
		String sqlString = "SELECT * FROM person_managment.users WHERE name=?";

		try {
			connection = DataSource.getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sqlString);
			statement.setString(1, name);
			connection.commit();
			result = statement.executeQuery();

			while (result.next()) {
				user = new User(result.getString("name"), result.getString("address"), result.getLong("phone"));
				user.setId(result.getLong("id"));
			}

		} catch (ClassNotFoundException | SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DataSource.returnConnection(connection);
		}
		return user;
	}

	@Override
	public void createTableIfNeeded() {
		Connection connection = null;
		PreparedStatement statement = null;
		String s1 = "CREATE SCHEMA IF NOT EXISTS `person_managment` DEFAULT CHARACTER SET utf8";
		String s2 = "USE `person_managment`";

		String s3="CREATE TABLE IF NOT EXISTS `person_managment`.`users` (" + "  `id` INT(11) NOT NULL AUTO_INCREMENT,"
				+ "  `name` VARCHAR(45) NOT NULL," + "  `address` VARCHAR(45) NOT NULL," + "  `phone` FLOAT NOT NULL,"
				+ "  PRIMARY KEY (`id`)," + "  UNIQUE INDEX `name_UNIQUE` (`name` ASC),"
				+ "  UNIQUE INDEX `id_UNIQUE` (`id` ASC))" + " ENGINE = InnoDB " + " DEFAULT CHARACTER SET = utf8";

		String[] statements = new String[] {s1,s2,s3};
		try {
			connection = DataSource.getConnection();
			for (String s: statements) {

				statement = connection.prepareStatement(s);
				statement.executeUpdate();

			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			DataSource.returnConnection(connection);
		}

	}

}
