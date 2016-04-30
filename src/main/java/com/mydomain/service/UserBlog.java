package com.mydomain.service;

import java.util.ArrayList;
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
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mydomain.infra.ServicesFactory;
import com.mydomain.model.Blog;
import com.mydomain.model.User;
import com.mydomain.model.dto.BlogDTO;
import com.mydomain.model.dto.UserDTO;

@Path("/blog")
public class UserBlog {
	
//	@GET
//	@Path("/{param}")
//	@Produces({MediaType.APPLICATION_JSON})
//	public User getUser(@PathParam("param") String name) {
//		ObjectId oid = null;
//		Datastore dataStore = ServicesFactory.getMongoDB();
//		try {
//			oid = new ObjectId(name);
//		} catch (Exception e) {// Ignore format errors
//		}
//		User user = (User) dataStore.createQuery(User.class).field("id")
//						.equal(oid);
//		return user;
//	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<BlogDTO> getBlogs() {
		Datastore dataStore = ServicesFactory.getMongoDB();
		List<Blog> blogs = dataStore.createQuery(Blog.class).asList();
		List<BlogDTO> blogsDtoList = new ArrayList();
		
		for(int index = 0; index < blogs.size(); index++){
			BlogDTO blogsDto = new BlogDTO();
			blogsDto.fillFromModel(blogs.get(index));
			blogsDtoList.add(blogsDto);
		}
		return blogsDtoList;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createUser(BlogDTO blogsDto){
		Blog blog = blogsDto.toModel();
		Datastore dataStore = ServicesFactory.getMongoDB();
		dataStore.save(blog);
	}
	
//	@PUT
//	@Consumes(MediaType.APPLICATION_JSON)
//	public void updateUser(UserDTO userDto){
//		User user = userDto.toModel();
//		Datastore dataStore = ServicesFactory.getMongoDB();
//		UpdateOperations<User> ops;
//		ops = dataStore.createUpdateOperations(User.class);
//		ops.set("name", 	user.getName());
//		ops.set("email", 	user.getEmail());
//		ops.set("password", user.getPassword());
//		ops.set("age", 		user.getAge());
//		Query<User> updateQuery = dataStore.createQuery(User.class).field("_id").equal(user.getId());
//		dataStore.update(updateQuery, ops);
//	}
	
//	@DELETE
//	@Path("/{param}")
//	@Produces({MediaType.APPLICATION_JSON})
//	public void deleteUser(@PathParam("param") String id) {
//		UserDTO userDto = new UserDTO();
//		User user = userDto.toModel(id);
//		Datastore dataStore = ServicesFactory.getMongoDB();
//		Query<User> deleteQuery = dataStore.createQuery(User.class).field("_id").equal(user.getId());
//		dataStore.delete(deleteQuery);
//	}
}
