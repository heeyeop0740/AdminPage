package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@Component
@Order(1)
public class ExceptionHandlerFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		}catch (ExpiredJwtException e) {
			setErrorResponse(response, e);
		}catch (JwtException | IllegalArgumentException e) {
			setErrorResponse(response, e);
		}
	}
	
	private void setErrorResponse(HttpServletResponse response, Exception e) {
		// TODO json 형태로 메시지를 전달할 수 있도록 변경
		
		ObjectMapper objectMapper = new ObjectMapper();
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		try {
			response.getWriter().write(objectMapper.writeValueAsString(e.getMessage()));
		}catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
