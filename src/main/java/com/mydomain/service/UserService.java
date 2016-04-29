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

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.mydomain.dao.UserDao;
import com.mydomain.model.User;

@Path("/user")
public class UserService {

	UserDao userDao = new UserDao();
	
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	
	@GET
	@Path("/{param}")
	@Produces({MediaType.APPLICATION_JSON})
	public User getUser(@PathParam("param") Integer id) {
		return userDao.getUser(id);
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> getUsers() {
		return userDao.getUsers();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//public void createUser(@FormParam("username") String username,@FormParam("password") String password,@FormParam("emailId") String emailId){
	public void createUser(User u){
		userDao.createUser(u);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//public void updateUser(@FormParam("username") String username,@FormParam("password") String password){
	public void updateUser(User u){
		userDao.updateUser(u);
	}
	
	@DELETE
	@Path("/{param}")
	@Produces({MediaType.APPLICATION_JSON})
	public boolean deleteUser(@PathParam("param") Integer id) {
		return userDao.deleteUser(id);
	}
	
}