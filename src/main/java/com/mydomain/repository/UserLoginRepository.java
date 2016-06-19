package com.mydomain.repository;

import com.mydomain.dao.UserDao;
import com.mydomain.model.User;
import com.mydomain.model.dto.UserDTO;

public class UserLoginRepository {
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean isAuthenticated(){
		System.out.println("UserLoginRepository.isAuthenticated() " +this.username);
		System.out.println("UserLoginRepository.isAuthenticated() " +this.password);
		
		UserDao userDao = new UserDao();
		
		UserDTO user = userDao.getUserName(username);
		
		if ((this.username == user.getName()) && (this.password == user.getPassword()) ) {
			return true;			
		}else{
			return false;
		}
	}

}
