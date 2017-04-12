package com.ee.Facade;

import java.util.Collection;

import com.ee.Bean.User;
import com.ee.DBDao.userDbDao;
import com.ee.Dao.UserDao;

public class UserFacade implements UserDao{

	@Override
	public Collection<User> getAllUsers() {
		userDbDao db = new userDbDao();
		Collection<User> allUsers = db.getAllUsers();
		return allUsers;
	}

	@Override
	public User upDateUser(User user) throws Exception {
		
		if (user == null) {
			throw new NullPointerException("Can`t update null user");
		}
		
		userDbDao db = new userDbDao();
		User userTocheck = db.getUserByName(user.getName());
		if (userTocheck == null){
			throw new Exception("User alredy exist");
		} else {
			return db.upDateUser(user);
		}
	}

	@Override
	public User addNewUser(User user) throws Exception {
		
		if (user == null) {
			throw new NullPointerException("Can`t update null user");
		}
		
		userDbDao db = new userDbDao();
		User userTocheck = db.getUserByName(user.getName());

		if (userTocheck == null){
			db.addNewUser(user);
		} else {
			throw new Exception("User alredy exist");
		}
		
		return db.getUserByName(user.getName());

	}

	@Override
	public String deleteUser(User user) throws Exception {
		
		if (user == null) {
			throw new NullPointerException("Can`t update null user");
		}
		
		userDbDao db = new userDbDao();
		return db.deleteUser(user);
	}

	@Override
	public User getUserById(long id) throws Exception {
		
		if (id <= 0) {
			throw new Exception("Id can`t be less then zero");
		}
		
		userDbDao db = new userDbDao();
		return db.getUserById(id);
	}

	@Override
	public User getUserByName(String name) {
		
		if (name == null) {
			return null;
		}
		
		userDbDao db = new userDbDao();
		return db.getUserByName(name);
	}
	
	@Override
	public void createTableIfNeeded() {
		userDbDao dao = new userDbDao();
		dao.createTableIfNeeded();
	}

}
