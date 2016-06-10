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
		List<Blog> blogs = dataStore.createQuery(Blog.class).order("-postedDate").asList();
		List<BlogDTO> blogsDtoList = new ArrayList();
		
		for(int index = 0; index < blogs.size(); index++){
			BlogDTO blogsDto = new BlogDTO();
			blogsDto.fillFromModel(blogs.get(index));
			blogsDtoList.add(blogsDto);
		}
		return blogsDtoList;
	}

	public List<BlogDTO> getBlogs(String tag, String userName, String orderBy) {
		Datastore dataStore = ServicesFactory.getMongoDB();
		
		Query<Blog> getQuery = dataStore.createQuery(Blog.class);
		
		if(tag != null){
			getQuery = getQuery.filter("tags = ", tag);
		}

		if(userName != null){
			getQuery = getQuery.filter("postedUserName = ", userName);
		}
		
		if((orderBy != null) && orderBy.equals("viewedCount")){
			getQuery = getQuery.order("-viewedCount");
		}else{
			getQuery = getQuery.order("-postedDate");
		}
		
		List<Blog> blogs = getQuery.asList();
		List<BlogDTO> blogsDtoList = new ArrayList();
		
		for(int index = 0; index < blogs.size(); index++){
			BlogDTO blogsDto = new BlogDTO();
			blogsDto.fillFromModel(blogs.get(index));
			blogsDtoList.add(blogsDto);
		}
		return blogsDtoList;
	}	
	
	public List<BlogDTO> doSearch(String searchstring,int limit,int offset) {
		List<BlogDTO> blogsDtoList = new ArrayList();
		Datastore dataStore = ServicesFactory.getMongoDB();
		/*
		Query<Blog> searchQuery = dataStore.createQuery(Blog.class);
		searchQuery.criteria("title").hasAnyOf(names),
		*/
		List<Blog> blogs = dataStore.createQuery(Blog.class)
                .search(searchstring)
                .order("-postedDate")
                .offset(offset)
                .limit(limit)
                .asList();
		
		for(int index = 0; index < blogs.size(); index++){
			BlogDTO blogsDto = new BlogDTO();
			blogsDto.fillFromModel(blogs.get(index));
			blogsDtoList.add(blogsDto);
		}
		return blogsDtoList;
	}

	public int getSearchCount(String searchstring) {
		Datastore dataStore = ServicesFactory.getMongoDB();
		List<Blog> blogs = dataStore.createQuery(Blog.class)
                .search(searchstring)
                .asList();
		return blogs.size();
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
		ops.set("synopsis", blog.getSynopsis());
		ops.set("content", 	blog.getContent());
		ops.set("posterUrl", blog.getPosterUrl());
		ops.set("tags", 	blog.getTags());
		ops.set("viewedCount", 	blog.getViewedCount());
		ops.set("postedDate",blog.getPostedDate());
		ops.set("postedUserName", 	blog.getPostedUserName());
		ops.set("postedUserId", 	blog.getPostedUserId());
		Query<Blog> updateQuery = dataStore.createQuery(Blog.class).field("_id").equal(blog.getId());
		dataStore.update(updateQuery, ops);
	}
	
	public int updateBlogViewedCount(String blogId) {
		int response_code = DaoReturnCodes.RETURN_STATUS_UNKNOWN_ERROR;
		BlogDTO blogsDto = new BlogDTO();
		try{
			Blog blog = blogsDto.toModel(blogId);
			Datastore dataStore = ServicesFactory.getMongoDB();
			Query<Blog> getQuery = dataStore.createQuery(Blog.class).field("_id").equal(blog.getId());
			Blog blogRequested = (Blog) getQuery.get();
			if(null != blogRequested){
				UpdateOperations<Blog> ops;
				ops = dataStore.createUpdateOperations(Blog.class);
				ops.set("title", 	blogRequested.getTitle());
				ops.set("synopsis", blogRequested.getSynopsis());
				ops.set("content", 	blogRequested.getContent());
				ops.set("posterUrl", blogRequested.getPosterUrl());
				ops.set("tags", 	blogRequested.getTags());
				ops.set("viewedCount", 	(blogRequested.getViewedCount() + 1));
				ops.set("postedDate",blogRequested.getPostedDate());
				ops.set("postedUserName", 	blogRequested.getPostedUserName());
				ops.set("postedUserId", 	blogRequested.getPostedUserId());
				Query<Blog> updateQuery = dataStore.createQuery(Blog.class).field("_id").equal(blog.getId());
				dataStore.update(updateQuery, ops);
				response_code = DaoReturnCodes.RETURN_STATUS_OK;
			}else{
				response_code = DaoReturnCodes.RETURN_STATUS_CONTENT_NOT_FOUND;
			}
		}catch(IllegalArgumentException execption){
    		System.out.println("BlogDTO.toModel() IllegalArgumentException");
    		response_code = DaoReturnCodes.RETURN_STATUS_INVALID_ARGUMENT;
    	}
		
		return response_code;
	}
	
	public void deleteBlog(String id) {
		BlogDTO blogsDto = new BlogDTO();
		Blog blog = blogsDto.toModel(id);
		Datastore dataStore = ServicesFactory.getMongoDB();
		Query<Blog> deleteQuery = dataStore.createQuery(Blog.class).field("_id").equal(blog.getId());
		dataStore.delete(deleteQuery);
	}
}
