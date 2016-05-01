package com.mydomain.dao;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mydomain.infra.ServicesFactory;
import com.mydomain.model.Blog;
import com.mydomain.model.dto.BlogDTO;

public class BlogDao {
	
	public BlogDTO getBlog(String id) {
		BlogDTO blogsDto = new BlogDTO();
		Blog blog = blogsDto.toModel(id);
		Datastore dataStore = ServicesFactory.getMongoDB();
		Query<Blog> getQuery = dataStore.createQuery(Blog.class).field("_id").equal(blog.getId());
		Blog blogRequested = (Blog) getQuery.get();
		BlogDTO blogsDtoResult = new BlogDTO();
		blogsDtoResult = blogsDtoResult.fillFromModel(blogRequested);
		return blogsDtoResult;
	}	
	
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

	public void createBlog(BlogDTO blogsDto){
		Blog blog = blogsDto.toModel();
		Datastore dataStore = ServicesFactory.getMongoDB();
		dataStore.save(blog);
	}
	
	public void updateBlog(BlogDTO blogsDto){
		Blog blog = blogsDto.toModel();
		Datastore dataStore = ServicesFactory.getMongoDB();
		UpdateOperations<Blog> ops;
		ops = dataStore.createUpdateOperations(Blog.class);
		ops.set("title", 	blog.getTitle());
		ops.set("content", 	blog.getContent());
		ops.set("tags", 	blog.getTags());
		ops.set("postedDate",blog.getPostedDate());
		ops.set("postedUserName", 	blog.getPostedUserName());
		ops.set("postedUserId", 	blog.getPostedUserId());
		Query<Blog> updateQuery = dataStore.createQuery(Blog.class).field("_id").equal(blog.getId());
		dataStore.update(updateQuery, ops);
	}
	
	public void deleteBlog(String id) {
		BlogDTO blogsDto = new BlogDTO();
		Blog blog = blogsDto.toModel(id);
		Datastore dataStore = ServicesFactory.getMongoDB();
		Query<Blog> deleteQuery = dataStore.createQuery(Blog.class).field("_id").equal(blog.getId());
		dataStore.delete(deleteQuery);
	}
}
