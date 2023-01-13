package com.example.demo.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsUtils;

import com.example.demo.service.LoginService;

@Component
@Order(2)
public class AuthFilter implements Filter{
	
	@Autowired
	LoginService loginService;
	
	@CrossOrigin
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		if(CorsUtils.isPreFlightRequest(req)) {
			chain.doFilter(request, response);
			return;
		}

		if(req.getRequestURI().equals("/login") || req.getRequestURI().contains("/register")) {
			chain.doFilter(request, response);
			return;
		}
		
		if(req.getRequestURI().equals("/login/refresh")) {
			if(loginService.validateToken(req, loginService.getKey(loginService.getRefreshTokenKeyString()))) {
				String token = loginService.getToken(req);
				request.setAttribute("id", loginService.getId(token, loginService.getRefreshTokenKeyString()));
				request.setAttribute("instId", loginService.getInstId(token, loginService.getRefreshTokenKeyString()));
				chain.doFilter(request, response);
				return;
			}
		}
		
		if(loginService.validateToken(req, loginService.getKey(loginService.getAcessTokenKeyString()))) {
			String token = loginService.getToken(req);
			request.setAttribute("id", loginService.getId(token, loginService.getAcessTokenKeyString()));
			request.setAttribute("instId", loginService.getInstId(token, loginService.getAcessTokenKeyString()));
			chain.doFilter(request, response);
		}
	}
	
	private void printRequestHeader(HttpServletRequest req) {
		Enumeration<String> headerNames = req.getHeaderNames();
		System.out.println("Request");
		while(headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			System.out.println(headerName + " : " +req.getHeader(headerName));
		}
	}

	private void printResponseHeader(HttpServletResponse res) {
		System.out.println("Response");
		Collection<String> resHeaderNames = res.getHeaderNames();
		Iterator<String> it = resHeaderNames.iterator();
		while(it.hasNext()) {
			String resheaderName = it.next();
			System.out.println(resheaderName + " : " + res.getHeader(resheaderName));
		}
	}
}
