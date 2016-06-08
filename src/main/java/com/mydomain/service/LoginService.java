package com.mydomain.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.mydomain.repository.UserAuthenticationRepository;
import com.mydomain.repository.UserLoginRepository;

@Path("login")
public class LoginService {
	@Context 
	private HttpServletRequest reqContext;
	@Context 
	private HttpServletResponse respContext;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserAuthenticationRepository getAuthenticatedUser(UserLoginRepository userLogin) throws IOException{
		UserAuthenticationRepository authenticatedUser = new UserAuthenticationRepository();
		
		System.out.println("LoginService.getAuthenticatedUser() : " +userLogin.getUsername());
		System.out.println("LoginService.getAuthenticatedUser() : " +userLogin.getPassword());
		
		if (userLogin.getPassword().equals("admin")) {
			HttpSession session=reqContext.getSession();
			session.setAttribute("userName", userLogin.getUsername());
			authenticatedUser.setUsername(userLogin.getUsername());
			authenticatedUser.setEmail("admin@admin.org");
			return authenticatedUser;
		}else{
			System.out.println("LoginService.getAuthenticatedUser() Invalid authenitcation details");
			respContext.sendError(401, "Invalid authenitcation details");
		}
		
		return null;
		
	}

}
