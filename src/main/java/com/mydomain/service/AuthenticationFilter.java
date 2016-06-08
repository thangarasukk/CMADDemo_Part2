package com.mydomain.service;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("AuthenticationFilter.init()");

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("AuthenticationFilter.doFilter()" +((HttpServletRequest) request).getRequestURI());
		System.out.println("AuthenticationFilter.doFilter() user" +((HttpServletRequest) request).getRequestURI().contains("/user"));
		System.out.println("AuthenticationFilter.doFilter() user" +((HttpServletRequest) request).getRequestURI().contains("/login"));
		if (((HttpServletRequest) request).getRequestURI().contains("/login") || ((HttpServletRequest) request).getRequestURI().contains("/user")) {
			chain.doFilter(request, response);
			return;
			
		}
		HttpSession session=((HttpServletRequest) request).getSession(false);
		System.out.println("AuthenticationFilter.doFilter() session: " +session);
		if(session!=null){
			chain.doFilter(request, response);
		}else{
//			((HttpServletResponse)response).setContentType(arg0);
			System.out.println("AuthenticationFilter.doFilter() sending 401");
			((HttpServletResponse)response).sendError(401, "Invalid authenitcation details");
		}

	}

	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("AuthenticationFilter.destroy()");

	}

}
