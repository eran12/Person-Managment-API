package com.ee.service;

import java.util.Collection;

import javax.jws.WebMethod;
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
	public User upDateUser( User user ) {

		if (user.getName() == null | user.getAddress() == null) {
			throw new WebApplicationException("Name or Adress can`t be null");
		}
		if(user.getPhone() < 0) {
				throw new WebApplicationException("Phone must be complete");
		}

		UserFacade fc = new UserFacade();
		User userUpdate = fc.getUserByName(user.getName());
		try {
			userUpdate.setAdress(user.getAddress());
			userUpdate.setPhone(user.getPhone());
			fc.upDateUser(userUpdate);
		} catch (Exception e) {
			throw new WebApplicationException(e.getMessage());
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
			throw new WebApplicationException("Name or Address can`t be null");
		}
		UserFacade fc = new UserFacade();
		try {
			fc.addNewUser(user);
			user = fc.getUserByName(user.getName());
		} catch (Exception e) {
				throw new WebApplicationException(e.getMessage());
		}
		return user;
	}

	@Override
	@Path("/deleteUser")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteUser(User user) {
		
		try {
			if (user.getName() == null) {
				throw new WebApplicationException("User can`t be null");
			} else if (user.getId() < 0 ) {
				throw new WebApplicationException("Id can`t be less then zero");
			}else{
			UserFacade fc = new UserFacade();
			
			user = fc.getUserByName(user.getName());
			if(user == null){
				return "User does not exsis";
			}else {
				return fc.deleteUser(user);
			}
			}
		} catch (Exception e) {
			throw new WebApplicationException(e.getMessage());
		}
	}

	@Override
	@Path("/userById")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserById(@QueryParam("id") long id) {
		try{
			if(id < 0){
				throw new WebApplicationException("Id can`t be less then zero");
			}else{
				UserFacade fc = new UserFacade();
				return fc.getUserById(id);
			}
		}catch (Exception e) {
			throw new WebApplicationException(e.getMessage());
		}
	}

	@Override
	@Path("/getByName")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@WebMethod(exclude = true)
	public User getUserByName(@QueryParam("name") String name) {
		if(name == null){
			return null;
		}
		UserFacade fc = new UserFacade();
		return fc.getUserByName(name);
	}

}
