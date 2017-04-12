package com.ee.service;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.ee.Bean.User;
import com.ee.Facade.UserFacade;

@Path("/user")

public class UserService implements UserServiceInterface{

	@Override
	@Path("/getall")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllUsers() {
		UserFacade fc = new UserFacade();
		return fc.getAllUsers();
	}

	@Override
	@Path("/edit")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User upDateUser( User user ) throws Exception {
			System.out.println("UserService.upDateUser()");
		if (user.getName() == null | user.getAddress() == null) {
			throw new NullPointerException("Name or Adress can`t be null");
		}
		if(user.getPhone() < 0) {
				throw new Exception("Phone must be complete");
		}

		UserFacade fc = new UserFacade();
		User userUpdate = fc.getUserByName(user.getName());
		try {
			userUpdate.setAdress(user.getAddress());
			userUpdate.setPhone(user.getPhone());
			fc.upDateUser(userUpdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			user = fc.getUserById(userUpdate.getId());
		} catch (Exception e) {

			return null;
		}
		return user;
	}

	@Override
	@Path("/newuser")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User addNewUser(User user)  {
			
		if (user.getName() == null | user.getAddress()== null) {
			throw new NullPointerException("Name or Address can`t be null");
		}
		UserFacade fc = new UserFacade();
		try {
			fc.addNewUser(user);
			user = fc.getUserByName(user.getName());
		} catch (Exception e) {
				throw new WebApplicationException(e);
		}
		return user;
	}

	@Override
	@Path("/deleteUser")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteUser(User user) {
		System.out.println("UserService.deleteUser()" + user);
		try {
			if (user.getName() == null) {
				throw new Exception("User can`t be null");
			} else if (user.getId() < 0 ) {
				throw new Exception("Id can`t be less then zero");
			}else{
			UserFacade fc = new UserFacade();
			
			user = fc.getUserByName(user.getName());
			if(user == null){
				return "User does not exsis";
			}else {
				String s2 = fc.deleteUser(user);
				System.out.println(s2);
				return s2;
			}
			}
		} catch (Exception e) {
			e.getMessage();

		}
		return null;
	}

	@Override
	@Path("/userById")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserById(@QueryParam("id") long id) {
		System.out.println("UserService.getUserById()");
		try{
			if(id < 0){
				throw new Exception("Id can`t be less then zero");
			}else{
				UserFacade fc = new UserFacade();
				return fc.getUserById(id);
			}
		}catch (Exception e) {
			e.getMessage();
		}
		return null;
	}

	@Override
	@Path("/getByName")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserByName(@QueryParam("name") String name) {
		System.out.println("UserService.getUserByName()");
		if(name == null){
			return null;
		}
		UserFacade fc = new UserFacade();
		return fc.getUserByName(name);
	}

}
