/*******************************************************************************
 * UserBlog.java, Created: Apr 29, 2016
 *
 * Part of CMAD Project - blog
 *
 * Copyright (c) 2016 : CISCO
 * 
 * P R O P R I E T A R Y & C O N F I D E N T I A L
 * 
 * ******************************************************************************
 */

package com.mydomain.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mydomain.dao.BlogDao;
import com.mydomain.model.dto.BlogDTO;

/**
 * class UserBlog
 * @author Thangarasu & Animesh
 * 
 */
@Path("/blog")
public class UserBlog {
	
	BlogDao blogDao = new BlogDao();
	
	public void setBlogDao(BlogDao blogDao){
		this.blogDao = blogDao;
	}
	
	/**
	 * Getting User Blog
	 * Rest URL : http://hostname:port/CMADDemo/rest/blog/id
	 * id - id value of blog. This is unique value created while blog is created
	 * 
	 * Example url: http://localhost:8080/CMADDemo/rest/blog/572776a8f63b961ab4aa6fe0
	 * 
	 * @param String id - id of the blog requested
	 * @return Blog that mataches the id given in JSON format
	 */
	@GET
	@Path("/{param}")
	@Produces({MediaType.APPLICATION_JSON})
	public BlogDTO getBlog(@PathParam("param") String id) {
		return blogDao.getBlog(id);
	}	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getBlogs() {
		List<BlogDTO> blogDtoResults =  blogDao.getBlogs();
		return Response.ok(blogDtoResults, MediaType.APPLICATION_JSON).build();
	}

	/* /rest/blog/search?searchstring="post"&offset=3&limit=1 */
	@GET  
	@Path("/search")  
	@Produces({MediaType.APPLICATION_JSON})
	public Response doSearch(  
		@QueryParam("searchstring") String searchstring,
		@DefaultValue("0") @QueryParam("offset") int offset,
		@DefaultValue("2") @QueryParam("limit") int limit) {  

		List<BlogDTO> blogDtoSearchResults = blogDao.doSearch(searchstring,limit,offset);
		return Response.ok(blogDtoSearchResults, MediaType.APPLICATION_JSON).build();
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
