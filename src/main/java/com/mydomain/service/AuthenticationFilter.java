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
		System.out.println("AuthenticationFilter.doFilter()");
		if (((HttpServletRequest) request).getRequestURI().contains("/login")) {
			chain.doFilter(request, response);
			return;
			
		}
		HttpSession session=((HttpServletRequest) request).getSession(false);
		if(session!=null){
			chain.doFilter(request, response);
		}else{
//			((HttpServletResponse)response).setContentType(arg0);
			((HttpServletResponse)response).sendError(401, "Invalid authenitcation details");
		}

	}

	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("AuthenticationFilter.destroy()");

	}

}
