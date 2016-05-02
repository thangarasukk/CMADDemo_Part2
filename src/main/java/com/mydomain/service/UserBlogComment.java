package com.mydomain.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.mongodb.morphia.Datastore;

import com.mydomain.dao.BlogCommentDao;
import com.mydomain.dao.BlogDao;
import com.mydomain.infra.ServicesFactory;
import com.mydomain.model.Blog;
import com.mydomain.model.BlogComment;
import com.mydomain.model.dto.BlogCommentDTO;
import com.mydomain.model.dto.BlogDTO;

@Path("/comment")
public class UserBlogComment {

	BlogCommentDao blogCommentDao = new BlogCommentDao();
	
	public void setBlogDao(BlogCommentDao blogCommentDao){
		this.blogCommentDao = blogCommentDao;
	}
	
	
//	public BlogDTO getBlog(String id) {
//		BlogDTO blogsDto = new BlogDTO();
//		Blog blog = blogsDto.toModel(id);
//		Datastore dataStore = ServicesFactory.getMongoDB();
//		Query<Blog> getQuery = dataStore.createQuery(Blog.class).field("_id").equal(blog.getId());
//		Blog blogRequested = (Blog) getQuery.get();
//		BlogDTO blogsDtoResult = new BlogDTO();
//		blogsDtoResult = blogsDtoResult.fillFromModel(blogRequested);
//		return blogsDtoResult;
//	}	
	
	@GET
	@Path("/{param}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<BlogCommentDTO> getBlogComments(@PathParam("param") String blogId) {
		return blogCommentDao.getBlogComments(blogId);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createBlogComment(BlogCommentDTO blogsCommentDto){
		blogCommentDao.createBlogComment(blogsCommentDto);
	}
	
	
//	public void updateBlog(BlogDTO blogsDto){
//		Blog blog = blogsDto.toModel();
//		Datastore dataStore = ServicesFactory.getMongoDB();
//		UpdateOperations<Blog> ops;
//		ops = dataStore.createUpdateOperations(Blog.class);
//		ops.set("title", 	blog.getTitle());
//		ops.set("content", 	blog.getContent());
//		ops.set("tags", 	blog.getTags());
//		ops.set("postedDate",blog.getPostedDate());
//		ops.set("postedUserName", 	blog.getPostedUserName());
//		ops.set("postedUserId", 	blog.getPostedUserId());
//		Query<Blog> updateQuery = dataStore.createQuery(Blog.class).field("_id").equal(blog.getId());
//		dataStore.update(updateQuery, ops);
//	}
//	
//	public void deleteBlog(String id) {
//		BlogDTO blogsDto = new BlogDTO();
//		Blog blog = blogsDto.toModel(id);
//		Datastore dataStore = ServicesFactory.getMongoDB();
//		Query<Blog> deleteQuery = dataStore.createQuery(Blog.class).field("_id").equal(blog.getId());
//		dataStore.delete(deleteQuery);
//	}
}
