package com.mydomain.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mydomain.dao.UserDao;
import com.mydomain.model.dto.UserDTO;

@Path("/user")
public class UserService {
	
	UserDao userDao = new UserDao();
	
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	
	@GET
	@Path("/{param}")
	@Produces({MediaType.APPLICATION_JSON})
	public UserDTO getUser(@PathParam("param") String id) {
		return userDao.getUser(id);
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<UserDTO> getUsers() {
		return userDao.getUsers();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createUser(UserDTO userDto){
		userDao.createUser(userDto);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(UserDTO userDto){
		userDao.updateUser(userDto);
	}
	
	@DELETE
	@Path("/{param}")
	@Produces({MediaType.APPLICATION_JSON})
	public void deleteUser(@PathParam("param") String id) {
		userDao.deleteUser(id);
	}
}
