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

import com.mydomain.dao.BlogDao;
import com.mydomain.model.dto.BlogDTO;

@Path("/blog")
public class UserBlog {
	
	BlogDao blogDao = new BlogDao();
	
	public void setBlogDao(BlogDao blogDao){
		this.blogDao = blogDao;
	}
	
	@GET
	@Path("/{param}")
	@Produces({MediaType.APPLICATION_JSON})
	public BlogDTO getBlog(@PathParam("param") String id) {
		return blogDao.getBlog(id);
	}	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<BlogDTO> getBlogs() {
		return blogDao.getBlogs();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createBlog(BlogDTO blogsDto){
		blogDao.createBlog(blogsDto);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateBlog(BlogDTO blogsDto){
		blogDao.updateBlog(blogsDto);
	}
	
	@DELETE
	@Path("/{param}")
	@Produces({MediaType.APPLICATION_JSON})
	public void deleteBlog(@PathParam("param") String id) {
		blogDao.deleteBlog(id);
	}
}
