package com.example.demo.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
			setErrorResponse(response, "Expired");
		}catch (JwtException | IllegalArgumentException e) {
			setErrorResponse(response, "IllegalArgument");
			System.out.println(e.getMessage());
		}
	}
	
	private void setErrorResponse(HttpServletResponse response, String message) {
		// TODO json 형태로 메시지를 전달할 수 있도록 변경
		// TODO 해당 에러를 이넘타입으로 변경하여 하드코딩 제거
		// TODO illegalargumentexception의 발생 원인 알아볼것
		
		ObjectMapper objectMapper = new ObjectMapper();
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		Map<String, Object> result = new HashMap<>();
		result.put("status", "failed");
		if(message.equals("Expired")) {
			result.put("message", "expired token");
		}
		if(message.equals("IllegalArgument"))
			result.put("message", "illegalargument");
		try {
			response.getWriter().write(objectMapper.writeValueAsString(result));
		}catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
