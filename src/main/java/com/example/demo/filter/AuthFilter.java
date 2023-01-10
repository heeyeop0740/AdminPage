package com.example.demo.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.service.LoginService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class AuthFilter implements Filter{
	
	@Autowired
	LoginService loginService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//[TODO] validateToken에서 발생한 error를 처리할 수 있는 방법에 대해 고안할 것.
		
		
		HttpServletRequest req = (HttpServletRequest)request;
		
		System.out.println(req.getHeader("authorization"));
//		Enumeration<String> headerNames = req.getHeaderNames();
//		
//		if(headerNames != null) {
//			while(headerNames.hasMoreElements()) {
//				System.out.println(headerNames.nextElement());
//				System.out.println("Header: " + req.getHeader(headerNames.nextElement()));
//			}
//		}
		System.out.println(req.getRequestURI());
		if(req.getRequestURI().contains("/login") || req.getRequestURI().contains("/register")) {
			chain.doFilter(request, response);
			return;
		}
		
		if(loginService.validateToken(req, loginService.getKey(LoginService.keyString))) {
			String token = loginService.getToken(req);
			request.setAttribute("id", loginService.getId(token));
			request.setAttribute("instId", loginService.getInstId(token));
			chain.doFilter(request, response);
		}
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public Map<String, Object> jwtExceptionCatcher(Exception ex) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("status", "token expired");	
	
		return result;
	}

}
