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

import com.mydomain.dao.BlogDao;
import com.mydomain.model.Blog;

@Path("/blog")
public class UserBlogs {

	BlogDao blogDao = new BlogDao();
	
	public void setBlogDao(BlogDao blogDao){
		this.blogDao = blogDao;
	}
	
	@GET
	@Path("/{param}")
	@Produces({MediaType.APPLICATION_JSON})
	public Blog getBlog(@PathParam("param") Integer id) {
		return blogDao.getBlog(id);
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Blog> getBlogs() {
		return blogDao.getBlogs();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//public void createBlog(@FormParam("userId") Integer userId,@FormParam("title") String title,@FormParam("data") String data){
	public void createBlog(Blog blog){
		blogDao.createBlog(blog);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//public void updateBlog(@FormParam("blogId") Integer blogId,@FormParam("userId") Integer userId,@FormParam("title") String title,@FormParam("data") String data){
	public void updateBlog(Blog blog){
		blogDao.updateBlog(blog);
	}
	
	@DELETE
	@Path("/{param}")
	@Produces({MediaType.APPLICATION_JSON})
	public boolean deleteBlog(@PathParam("param") Integer id) {
		return blogDao.deleteBlog(id);
	}
	
}