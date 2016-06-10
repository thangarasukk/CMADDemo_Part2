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

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mydomain.dao.BlogDao;
import com.mydomain.model.dto.BlogDTO;
import com.mydomain.model.dto.Count;

/**
 * 
 * <dl><dt><span class="strong">Description :</span></dt>
 * 		<dd>This Class implements rest APIs for user blogs
 * 		</dd>
 * @author Thangarasu & Animesh
 * @version 1.0
 * @since   2016-04-29 
 */
@Path("/blog")
public class UserBlog {
	@Context 
	private HttpServletRequest reqContext;
	@Context 
	private HttpServletResponse respContext;
	
	BlogDao blogDao = new BlogDao();
	
	public void setBlogDao(BlogDao blogDao){
		this.blogDao = blogDao;
	}
	
	/**
	 * <dl><dt><span class="strong">Description :</span></dt>
	 * 		<dd>This API is used to get User Blog of the id given
	 * 		</dd>
	 * <dl><dt><span class="strong">Rest URL :</span></dt>
	 * 		<dd><code>http://hostname:port/CMADDemo/rest/blog/id </code>
	 * 			<p><code>id</code> - id value of blog. This is unique value created while blog is created
	 * 		</dd>
	 * <p>
	 * <dl><dt><span class="strong">Example url:</span></dt>
	 * 		<dd><code>http://localhost:8080/CMADDemo/rest/blog/572776a8f63b961ab4aa6fe0 </code></dd>
	 * <p>
	 * <dl><dt><span class="strong">Sample response:</span></dt>
	 * <dd><code>
	 * {<p>
	 * 		"title": "4th post",<p>
	 * 		"content": "This is 4 post",<p>
	 * 		"tags": null,<p>
	 * 		"postedDate": 1462204072139,<p>
	 * 		"postedUserName": "Hari",<p>
	 * 		"postedUserId": "5722ffa441fbd6d042b07202",<p>
	 * 		"id": "572776a8f63b961ab4aa6fe0"<p>
	 * }<p>
	 * </code></dd>
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

	/**
	 * <dl><dt><span class="strong">Description :</span></dt>
	 * 		<dd>This API is used to get all User Blogs<p>
	 * 			Sorted by posted date; latest post shall in the top
	 * 		</dd>
	 * <p>
	 * <dl><dt><span class="strong">Rest URL(s) :</span></dt>
	 * 		<dd><code>http://hostname:port/CMADDemo/rest/blog </code><p></dd>
	 * 		<dd><code>http://hostname:port/CMADDemo/rest/blog?tag=movies </code><p></dd>
	 * 		<dd><code>http://hostname:port/CMADDemo/rest/blog?userName=xyz </code><p></dd> 
	 * 		<dd><code>http://hostname:port/CMADDemo/rest/blog?orderBy=viewedCount </code><p></dd>
	 * <p>
	 * <dl><dt><span class="strong">Example url:</span></dt>
	 * 		<dd><code>http://localhost:8080/CMADDemo/rest/blog </code></dd>
	 * <p>
	 * <dl><dt><span class="strong">Sample response:</span></dt>
	 * <dd><code>
	 * {<p>
	 * 		"title": "4th post",<p>
	 * 		"content": "This is 4 post",<p>
	 * 		"tags": null,<p>
	 * 		"postedDate": 1462204072139,<p>
	 * 		"postedUserName": "Hari",<p>
	 * 		"postedUserId": "5722ffa441fbd6d042b07202",<p>
	 * 		"id": "572776a8f63b961ab4aa6fe0"<p>
	 * }<p>
	 * </code></dd>
	 * 
	 * @param None
	 * @return All user Blogs in JSON format
	 * @throws IOException 
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getBlogs(
		@QueryParam("tag") String tag,
		@QueryParam("userName") String userName,
		@QueryParam("orderBy") String orderBy
		) throws IOException {

		System.out.println("UserBlog.getBlogs() tag = " + tag);
		System.out.println("UserBlog.getBlogs() userName = " + userName);
		System.out.println("UserBlog.getBlogs() orderBy = " + orderBy);

		HttpSession session=reqContext.getSession(false);
		if(session!=null){  
			System.out.println("UserBlog.getBlogs()");
			List<BlogDTO> blogDtoResults =  blogDao.getBlogs(tag,userName,orderBy);
			return Response.ok(blogDtoResults, MediaType.APPLICATION_JSON).build();			
		}else{
			respContext.sendError(401, "Invalid authenitcation details");
			return null;
		}
	}
	
	/**
	 * <dl><dt><span class="strong">Description :</span></dt>
	 * 		<dd>This API is used to get count of all User Blogs<p>
	 * 		</dd>
	 * <p>
	 * <dl><dt><span class="strong">Rest URL :</span></dt>
	 * 		<dd><code>http://hostname:port/CMADDemo/rest/blog/count </code><p>
	 * 		</dd>
	 * <p>
	 * <dl><dt><span class="strong">Example url:</span></dt>
	 * 		<dd><code>http://localhost:8080/CMADDemo/rest/blog/count </code></dd>
	 * <p>
	 * <dl><dt><span class="strong">Sample response:</span></dt>
	 * <dd><code>
	 * {<p>
	 * 		"count": 4<p>
	 * }<p>
	 * </code></dd>
	 * 
	 * @param None
	 * @return Count of all user Blogs in JSON format
	 */
	@GET  
	@Path("/count")  
	@Produces({MediaType.APPLICATION_JSON})
	public Count getCount() {
		List<BlogDTO> blogDtoResults =  blogDao.getBlogs();
		return new Count(blogDtoResults.size());
	}

	/**
	 * <dl><dt><span class="strong">Description :</span></dt>
	 * 		<dd>Search in all User Blogs<p>
	 * 			Sorted by posted date; latest post shall in the top
	 * 		</dd>
	 * <p>
	 * <dl><dt><span class="strong">Rest URL :</span></dt>
	 * 		<dd><code>http://hostname:port/CMADDemo/rest/blog/search?searchstring=searchstringvalue&offset=offsetvalue&limit=limitvalue</code><p>
	 *			 <p><code>searchstringvalue</code> - search string
	 *			 <p><code>offsetvalue</code> (optional) - skip offset of search result list; default value : 0
	 *			 <p><code>limitvalue</code> (optional) - page size of search result list; default value : 2
	 * 		</dd>
	 * <p>
	 * <dl><dt><span class="strong">Example url:</span></dt>
	 * 		<dd><code>http://localhost:8080/CMADDemo/rest/blog/search?searchstring="post"&offset=0&limit=5 </code></dd>
	 * <p>
	 * <dl><dt><span class="strong">Sample response:</span></dt>
	 * <dd><code>
	 * {<p>
	 * 		"title": "4th post",<p>
	 * 		"content": "This is 4 post",<p>
	 * 		"tags": null,<p>
	 * 		"postedDate": 1462204072139,<p>
	 * 		"postedUserName": "Hari",<p>
	 * 		"postedUserId": "5722ffa441fbd6d042b07202",<p>
	 * 		"id": "572776a8f63b961ab4aa6fe0"<p>
	 * }<p>
	 * </code></dd>
	 * 
	 * @param None
	 * @return Search result of user Blogs in JSON format
	 */
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
	
	/**
	 * <dl><dt><span class="strong">Description :</span></dt>
	 * 		<dd>Count of Search in all User Blogs
	 * 		</dd>
	 * <p>
	 * <dl><dt><span class="strong">Rest URL :</span></dt>
	 * 		<dd><code>http://hostname:port/CMADDemo/rest/blog/search?searchstring=searchstringvalue</code><p>
	 *			 <p><code>searchstringvalue</code> - search string
	 * 		</dd>
	 * <p>
	 * <dl><dt><span class="strong">Example url:</span></dt>
	 * 		<dd><code>http://localhost:8080/CMADDemo/rest/blog/search?searchstring="post"</code></dd>
	 * <p>
	 * <dl><dt><span class="strong">Sample response:</span></dt>
	 * <dd><code>
	 * {<p>
	 * 		"count": 3<p>
	 * }<p>
	 * </code></dd>
	 * 
	 * @param None
	 * @return Search result count of user Blogs in JSON format
	 */
	@GET  
	@Path("/searchcount")  
	@Produces({MediaType.APPLICATION_JSON})
	public Count getSearchCount(  
		@QueryParam("searchstring") String searchstring) {  
		return new Count(blogDao.getSearchCount(searchstring));
	}
	
	/**
	 * <dl><dt><span class="strong">Description :</span></dt>
	 * 		<dd>This API creates the blog<p>
	 * 		</dd>
	 * <p>
	 * <dl><dt><span class="strong">Rest URL :</span></dt>
	 * 		<dd><code>http://hostname:port/CMADDemo/rest/blog/blogdetails </code><p>
	 * 		</dd>
	 * <p>
	 * <dl><dt><span class="strong">Example url:</span></dt>
	 * 		<dd><code>http://localhost:8080/CMADDemo/rest/blog</code><p>
	 * Param : <p>
	 *<code>
	 * {<p>
	 * 		"title": "4th post",<p>
	 * 		"content": "This is 4 post",<p>
	 * 		"tags": null,<p>
	 * 		"postedDate": 1462204072139,<p>
	 * 		"postedUserName": "Hari",<p>
	 * 		"postedUserId": "5722ffa441fbd6d042b07202"<p>
	 * }<p>
	 * </code></dd>
	 * 
	 * @param User blog in JSON format
	 * @return None
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createBlog(BlogDTO blogsDto){
		blogDao.createBlog(blogsDto);
	}
	
	/**
	 * <dl><dt><span class="strong">Description :</span></dt>
	 * 		<dd>This API updates the blog given<p>
	 * 		</dd>
	 * <p>
	 * <dl><dt><span class="strong">Rest URL :</span></dt>
	 * 		<dd><code>http://hostname:port/CMADDemo/rest/blog/update/blogdetails </code><p>
	 * 		</dd>
	 * <p>
	 * <dl><dt><span class="strong">Example url:</span></dt>
	 * 		<dd><code>http://localhost:8080/CMADDemo/rest/blog/update</code><p>
	 * Param : <p>
	 *<code>
	 * {<p>
	 * 		"title": "4th post",<p>
	 * 		"content": "This is 4 post",<p>
	 * 		"tags": null,<p>
	 * 		"postedDate": 1462204072139,<p>
	 * 		"postedUserName": "Hari",<p>
	 * 		"postedUserId": "5722ffa441fbd6d042b07202",<p>
	 * 		"id": "572776a8f63b961ab4aa6fe0"<p>
	 * }<p>
	 * </code></dd>
	 * 
	 * @param User blog in JSON format
	 * @return None
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateBlog(BlogDTO blogsDto){
		blogDao.updateBlog(blogsDto);
	}
	
	/**
	 * <dl><dt><span class="strong">Description :</span></dt>
	 * 		<dd>This API deletes the blog of the id given<p>
	 * 		</dd>
	 * <p>
	 * <dl><dt><span class="strong">Rest URL :</span></dt>
	 * 		<dd><code>http://hostname:port/CMADDemo/rest/blog/delete/id </code><p>
	 * 		</dd>
	 * <p>
	 * <dl><dt><span class="strong">Example url:</span></dt>
	 * 		<dd><code>http://localhost:8080/CMADDemo/rest/blog/delete/572776a8f63b961ab4aa6fe0 </code></dd>
	 * <p>
	 * </code></dd>
	 * 
	 * @param String id - id of the blog requested
	 * @return None
	 */
	@DELETE
	@Path("/{param}")
	@Produces({MediaType.APPLICATION_JSON})
	public void deleteBlog(@PathParam("param") String id) {
		blogDao.deleteBlog(id);
	}
}
