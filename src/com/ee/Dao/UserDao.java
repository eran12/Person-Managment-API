package com.ee.Dao;

import java.util.Collection;

import com.ee.Bean.User;

public interface UserDao {

	public Collection<User> getAllUsers();
	
	public User upDateUser(User user) throws Exception;
	
	public User addNewUser(User user) throws Exception;
	
	public String deleteUser(User user) throws Exception;
	
	public User getUserById(long id) throws Exception;
	
	public User getUserByName(String name);
	
	public void createTableIfNeeded();
}
