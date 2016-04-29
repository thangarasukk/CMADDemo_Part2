package com.mydomain.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import com.mydomain.dao.UserDao;
import com.mydomain.model.User;
import junit.framework.Assert;

public class UserServiceTest {

	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testGetUser(){
		//valid id
		UserDao mockUserDao = mock(UserDao.class);
		User mockUser = new User();
		mockUser.setUsername("Thanga");
		Mockito.when(mockUserDao.getUser(1)).thenReturn(mockUser);
		
		UserService userService = new UserService();
		userService.setUserDao(mockUserDao);
		User user = userService.getUser(1);
		Assert.assertEquals("Thanga", user.getUsername());

		//null id
		Mockito.when(mockUserDao.getUser(null)).thenThrow(new IllegalArgumentException("null id"));
		User userNull = userService.getUser(null);
		
	}
	
	@Test 
	public void testGetUsers(){
		//valid id
		UserDao mockUserDao = mock(UserDao.class);

		User mockUser1 = new User();
		User mockUser2 = new User();
		mockUser1.setUsername("Thanga");
		mockUser2.setUsername("Animesh");
		
		List<User> mockUserList = new ArrayList<User>();
		mockUserList.add(mockUser1);
		mockUserList.add(mockUser2);
		
		Mockito.when(mockUserDao.getUsers()).thenReturn(mockUserList);
		
		UserService userService = new UserService();
		userService.setUserDao(mockUserDao);
		List<User> userList = userService.getUsers();
		
		User user1 = userList.get(0);
		Assert.assertEquals("Thanga", user1.getUsername());
		User user2 = userList.get(1);
		Assert.assertEquals("Animesh", user2.getUsername());
	}
	
	@Test 
	public void testCreateUser(){
		UserDao mockUserDao = mock(UserDao.class);
		User mockUser = new User();
		mockUser.setUsername("Thanga");
		mockUser.setPassword("Rasu");

		UserService userService = new UserService();
		userService.setUserDao(mockUserDao);
		userService.createUser(mockUser);
		
		Mockito.verify(mockUserDao, Mockito.times(1)).createUser(mockUser);
	}
	
	@Test 
	public void testUpdateUser(){
		UserDao mockUserDao = mock(UserDao.class);
		User mockUser = new User();
		mockUser.setUsername("Thanga");
		mockUser.setPassword("Rasu");

		UserService userService = new UserService();
		userService.setUserDao(mockUserDao);
		userService.updateUser(mockUser);
		
		Mockito.verify(mockUserDao, Mockito.times(1)).updateUser(mockUser);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testDeleteUser(){
		UserDao mockUserDao = mock(UserDao.class);

		UserService userService = new UserService();
		userService.setUserDao(mockUserDao);
		userService.deleteUser(1);
		
		Mockito.verify(mockUserDao, Mockito.times(1)).deleteUser(1);
		
		//null id
		Mockito.when(mockUserDao.deleteUser(null)).thenThrow(new IllegalArgumentException("null id"));
		userService.deleteUser(null);
	}
}