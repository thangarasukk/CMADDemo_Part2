package com.mydomain.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import com.mydomain.model.User;

@Path("/user")
public class UserService {
	
	@GET
	@Path("/{param}")
	@Produces({MediaType.APPLICATION_JSON})
	public User getUser(@PathParam("param") String name) {
		ObjectId oid = null;
		Datastore dataStore = ServicesFactory.getMongoDB();
		try {
			oid = new ObjectId(name);
		} catch (Exception e) {// Ignore format errors
		}
		User user = (User) dataStore.createQuery(User.class).field("id")
						.equal(oid);
		return user;
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> getUsers() {
		Datastore dataStore = ServicesFactory.getMongoDB();
		List<User> users = dataStore.createQuery(User.class).asList();
		return users;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createUser(User u){
		Datastore dataStore = ServicesFactory.getMongoDB();
		dataStore.save(u);
	}
	

	
}
