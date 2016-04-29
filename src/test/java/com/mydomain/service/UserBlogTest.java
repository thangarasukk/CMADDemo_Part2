package com.mydomain.service;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.mydomain.dao.BlogDao;
import com.mydomain.model.Blog;
import junit.framework.Assert;

public class UserBlogTest {

	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testGetBlog(){
		//valid id
		BlogDao mockBlogDao = mock(BlogDao.class);
		Blog mockBlog = new Blog();
		mockBlog.setTitle("Thanga");
		Mockito.when(mockBlogDao.getBlog(1)).thenReturn(mockBlog);
		
		UserBlogs userBlogs = new UserBlogs();
		userBlogs.setBlogDao(mockBlogDao);
		Blog blog = userBlogs.getBlog(1);
		Assert.assertEquals("Thanga", blog.getTitle());

		//null id
		Mockito.when(mockBlogDao.getBlog(null)).thenThrow(new IllegalArgumentException("null id"));
		Blog blogNull = userBlogs.getBlog(null);
	}
	
	@Test 
	public void testGetBlogs(){
		//valid id
		BlogDao mockBlogDao = mock(BlogDao.class);

		Blog mockBlog1 = new Blog();
		Blog mockBlog2 = new Blog();
		mockBlog1.setTitle("Thanga");
		mockBlog2.setTitle("Animesh");
		
		List<Blog> mockBlogList = new ArrayList<Blog>();
		mockBlogList.add(mockBlog1);
		mockBlogList.add(mockBlog2);
		
		Mockito.when(mockBlogDao.getBlogs()).thenReturn(mockBlogList);
		
		UserBlogs userBlogs = new UserBlogs();
		userBlogs.setBlogDao(mockBlogDao);
		List<Blog> blogList = userBlogs.getBlogs();
		
		Blog blog1 = blogList.get(0);
		Assert.assertEquals("Thanga", blog1.getTitle());
		Blog blog2 = blogList.get(1);
		Assert.assertEquals("Animesh", blog2.getTitle());
	}
	
	@Test 
	public void testCreateBlog(){
		BlogDao mockBlogDao = mock(BlogDao.class);
		Blog mockBlog = new Blog();
		mockBlog.setTitle("Thanga");
		mockBlog.setData("Rasu");

		UserBlogs userBlogs = new UserBlogs();
		userBlogs.setBlogDao(mockBlogDao);
		userBlogs.createBlog(mockBlog);
		
		Mockito.verify(mockBlogDao, Mockito.times(1)).createBlog(mockBlog);
	}
	
	@Test 
	public void testUpdateBlog(){
		BlogDao mockBlogDao = mock(BlogDao.class);
		Blog mockBlog = new Blog();
		mockBlog.setTitle("Thanga");
		mockBlog.setData("Rasu");

		UserBlogs userBlogs = new UserBlogs();
		userBlogs.setBlogDao(mockBlogDao);
		userBlogs.updateBlog(mockBlog);;
		
		Mockito.verify(mockBlogDao, Mockito.times(1)).updateBlog(mockBlog);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testDeleteBlog(){
		BlogDao mockBlogDao = mock(BlogDao.class);

		UserBlogs userBlogs = new UserBlogs();
		userBlogs.setBlogDao(mockBlogDao);
		userBlogs.deleteBlog(1);
		
		Mockito.verify(mockBlogDao, Mockito.times(1)).deleteBlog(1);
		
		//null id
		Mockito.when(mockBlogDao.deleteBlog(null)).thenThrow(new IllegalArgumentException("null id"));
		userBlogs.deleteBlog(null);
	}
}