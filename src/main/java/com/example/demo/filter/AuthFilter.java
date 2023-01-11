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

import com.example.demo.service.LoginService;

@Component
@CrossOrigin
@Order(2)
public class AuthFilter implements Filter{
	
	@Autowired
	LoginService loginService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
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
	
	/**
	 * request header 내용 콘솔에 print하여 확인하는 함수
	 * @param req
	 */
	private void getRequestInfo(HttpServletRequest req) {
		
		Enumeration<String> headerNames = req.getHeaderNames();
		System.out.println("Request");
		while(headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			System.out.println(headerName + " : " +req.getHeader(headerName));
		}
	}
	
	/**
	 * response header 내용 콘솔에 print하여 확인하는 함수
	 * @param res
	 */
	private void getResponseInfo(HttpServletResponse res) {
		Collection<String> resHeaderNames = res.getHeaderNames();
		Iterator<String> it = resHeaderNames.iterator();
		
		System.out.println("Response");
		while(it.hasNext()) {
			String resheaderName = it.next();
			System.out.println(resheaderName + " : " + res.getHeader(resheaderName));
		}
	}
}
