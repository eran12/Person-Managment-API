package com.ee.service;

import java.util.Collection;

import com.ee.Bean.User;

public interface UserServiceInterface {

	public User getUserById(long id);

	public User getUserByName(String name);

	public Collection<User> getAllUsers();

	public User addNewUser(User userJson);

	public String deleteUser(User user);

	public User upDateUser(User user) throws Exception;

	
}
