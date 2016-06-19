package com.mydomain.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import com.mydomain.dao.BlogDao;
import com.mydomain.dao.DaoReturnCodes;
import com.mydomain.model.dto.BlogDTO;
import com.mydomain.model.dto.Count;

import junit.framework.Assert;

public class UserBlogTest {

	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void testGetBlog_validArg(){
		BlogDao mockBlogDao = mock(BlogDao.class);
		BlogDTO mockBlogDto = new BlogDTO();
		mockBlogDto.setTitle("Thanga");
		Mockito.when(mockBlogDao.getBlog("1")).thenReturn(mockBlogDto);
		UserBlog userBlog = new UserBlog();
		userBlog.setBlogDao(mockBlogDao);

		BlogDTO blogDto = userBlog.getBlog("1");
		
		Assert.assertEquals("Thanga", blogDto.getTitle());
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testGetBlog_invalidArg(){
		BlogDao mockBlogDao = mock(BlogDao.class);
		UserBlog userBlog = new UserBlog();
		userBlog.setBlogDao(mockBlogDao);

		Mockito.when(mockBlogDao.getBlog(null)).thenThrow(new IllegalArgumentException("null id"));
		BlogDTO blogDtoNull = userBlog.getBlog(null);
	}
	
	@Test 
	public void testGetBlogs_ValidSession(){
		BlogDTO mockBlogsDto1 = new BlogDTO();
		mockBlogsDto1.setTitle("TestTitle1");
		mockBlogsDto1.setContent("TestContent1");
		BlogDTO mockBlogsDto2 = new BlogDTO();
		mockBlogsDto2.setTitle("TestTitle2");
		mockBlogsDto2.setContent("TestContent2");
		List<BlogDTO> mockBlogDtoList = new ArrayList<BlogDTO>();
		mockBlogDtoList.add(mockBlogsDto1);
		mockBlogDtoList.add(mockBlogsDto2);
		String tag = "testTag";
		String userName = "testUserName";
		String orderBy = "updatedDate";
		BlogDao mockBlogDao = mock(BlogDao.class);
		Mockito.when(mockBlogDao.getBlogs(tag,userName,orderBy)).thenReturn(mockBlogDtoList);
		UserBlog userBlog = new UserBlog();
		HttpServletRequest reqContext = mock(HttpServletRequest.class);
		Whitebox.setInternalState(userBlog, "reqContext", reqContext);
		HttpServletResponse respContext = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		Mockito.when(reqContext.getSession(false)).thenReturn(session);
		Whitebox.setInternalState(userBlog, "respContext", respContext);
		userBlog.setBlogDao(mockBlogDao);
		
		try {
			Response response = userBlog.getBlogs(tag,userName,orderBy);

			List<BlogDTO> blogDtoList = (List<BlogDTO>) response.getEntity();
			BlogDTO blogDto1 = blogDtoList.get(0);
			Assert.assertEquals("TestTitle1", blogDto1.getTitle());
			BlogDTO blogDto2 = blogDtoList.get(1);
			Assert.assertEquals("TestTitle2", blogDto2.getTitle());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test 
	public void testGetBlogs_InvalidSession(){
		String tag = "testTag";
		String userName = "testUserName";
		String orderBy = "updatedDate";
		BlogDao mockBlogDao = mock(BlogDao.class);
		UserBlog userBlog = new UserBlog();
		HttpServletRequest reqContext = mock(HttpServletRequest.class);
		Whitebox.setInternalState(userBlog, "reqContext", reqContext);
		HttpServletResponse respContext = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		Mockito.when(reqContext.getSession(false)).thenReturn(null);
		Whitebox.setInternalState(userBlog, "respContext", respContext);
		userBlog.setBlogDao(mockBlogDao);
		
		try {
			Response response = userBlog.getBlogs(tag,userName,orderBy);
			Assert.assertEquals(null, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test 
	public void testGetSearchCount_ValidArg(){
		BlogDao mockBlogDao = mock(BlogDao.class);
		UserBlog userBlog = new UserBlog();
		userBlog.setBlogDao(mockBlogDao);
		Mockito.when(mockBlogDao.getSearchCount("TestSearch")).thenReturn(2);

		Count searchCount = userBlog.getSearchCount("TestSearch");
		
		Mockito.verify(mockBlogDao, Mockito.times(1)).getSearchCount("TestSearch");
		assertEquals(2, searchCount.getCount());
	}
	
	@Test 
	public void testCreateBlog_ValidArg(){
		BlogDao mockBlogDao = mock(BlogDao.class);
		BlogDTO mockBlogsDto = new BlogDTO();
		mockBlogsDto.setTitle("TestTitle");
		mockBlogsDto.setContent("TestContent");
		UserBlog userBlog = new UserBlog();
		userBlog.setBlogDao(mockBlogDao);
		Mockito.when(mockBlogDao.createBlog(mockBlogsDto)).thenReturn(DaoReturnCodes.RETURN_STATUS_OK);

		Response response = userBlog.createBlog(mockBlogsDto);
		
		Mockito.verify(mockBlogDao, Mockito.times(1)).createBlog(mockBlogsDto);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test 
	public void testCreateBlog_NullArg(){
		BlogDao mockBlogDao = mock(BlogDao.class);
		UserBlog userBlog = new UserBlog();
		userBlog.setBlogDao(mockBlogDao);
		Mockito.when(mockBlogDao.createBlog(null)).thenReturn(DaoReturnCodes.RETURN_STATUS_INVALID_ARGUMENT);

		Response response = userBlog.createBlog(null);
		
		Mockito.verify(mockBlogDao, Mockito.times(1)).createBlog(null);
		assertEquals(Response.Status.EXPECTATION_FAILED.getStatusCode(), response.getStatus());
	}
	
	@Test 
	public void testUpdateBlog_ValidArg(){
		BlogDao mockBlogDao = mock(BlogDao.class);
		BlogDTO mockBlogsDto = new BlogDTO();
		mockBlogsDto.setTitle("TestTitle");
		mockBlogsDto.setContent("TestContent");
		UserBlog userBlog = new UserBlog();
		userBlog.setBlogDao(mockBlogDao);
		Mockito.when(mockBlogDao.updateBlog(mockBlogsDto)).thenReturn(DaoReturnCodes.RETURN_STATUS_OK);
		
		Response response = userBlog.updateBlog(mockBlogsDto);
		
		Mockito.verify(mockBlogDao, Mockito.times(1)).updateBlog(mockBlogsDto);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test 
	public void testUpdateBlog_NullArg(){
		BlogDao mockBlogDao = mock(BlogDao.class);
		UserBlog userBlog = new UserBlog();
		userBlog.setBlogDao(mockBlogDao);
		Mockito.when(mockBlogDao.updateBlog(null)).thenReturn(DaoReturnCodes.RETURN_STATUS_INVALID_ARGUMENT);
		
		Response response = userBlog.updateBlog(null);
		
		Mockito.verify(mockBlogDao, Mockito.times(1)).updateBlog(null);
		assertEquals(Response.Status.EXPECTATION_FAILED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testUpdateBlogViewedCountBlog_ValidArg(){
		BlogDao mockBlogDao = mock(BlogDao.class);
		UserBlog userBlog = new UserBlog();
		userBlog.setBlogDao(mockBlogDao);
		Mockito.when(mockBlogDao.updateBlogViewedCount("valid")).thenReturn(DaoReturnCodes.RETURN_STATUS_OK);
		
		Response response = userBlog.updateBlogViewedCount("valid");

		Mockito.verify(mockBlogDao, Mockito.times(1)).updateBlogViewedCount("valid");
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testUpdateBlogViewedCountBlog_InvalidArg(){
		BlogDao mockBlogDao = mock(BlogDao.class);
		UserBlog userBlog = new UserBlog();
		userBlog.setBlogDao(mockBlogDao);
		Mockito.when(mockBlogDao.updateBlogViewedCount("Invalid")).thenReturn(DaoReturnCodes.RETURN_STATUS_INVALID_ARGUMENT);
		
		Response response = userBlog.updateBlogViewedCount("Invalid");

		Mockito.verify(mockBlogDao, Mockito.times(1)).updateBlogViewedCount("Invalid");
		assertEquals(Response.Status.EXPECTATION_FAILED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testDeleteBlog_ValidArg(){
		BlogDao mockBlogDao = mock(BlogDao.class);
		UserBlog userBlog = new UserBlog();
		userBlog.setBlogDao(mockBlogDao);
		Mockito.when(mockBlogDao.deleteBlog("valid")).thenReturn(DaoReturnCodes.RETURN_STATUS_OK);
		
		Response response = userBlog.deleteBlog("valid");

		Mockito.verify(mockBlogDao, Mockito.times(1)).deleteBlog("valid");
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testDeleteBlog_NullArg(){
		BlogDao mockBlogDao = mock(BlogDao.class);
		UserBlog userBlog = new UserBlog();
		userBlog.setBlogDao(mockBlogDao);
		Mockito.when(mockBlogDao.deleteBlog(null)).thenReturn(DaoReturnCodes.RETURN_STATUS_INVALID_ARGUMENT);
		
		Response response = userBlog.deleteBlog(null);

		Mockito.verify(mockBlogDao, Mockito.times(1)).deleteBlog(null);
		assertEquals(Response.Status.EXPECTATION_FAILED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testDeleteBlog_InvalidArg(){
		BlogDao mockBlogDao = mock(BlogDao.class);
		UserBlog userBlog = new UserBlog();
		userBlog.setBlogDao(mockBlogDao);
		Mockito.when(mockBlogDao.deleteBlog("Invalid")).thenReturn(DaoReturnCodes.RETURN_STATUS_INVALID_ARGUMENT);
		
		Response response = userBlog.deleteBlog("Invalid");

		Mockito.verify(mockBlogDao, Mockito.times(1)).deleteBlog("Invalid");
		assertEquals(Response.Status.EXPECTATION_FAILED.getStatusCode(), response.getStatus());
	}
}