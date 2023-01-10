package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserInfo;
import com.example.demo.service.UserService;

import io.jsonwebtoken.JwtException;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/userInfo")
	public Map<String, Object> getUserInfo(HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<>();
		UserInfo userInfo;
		
		if((userInfo = userService.getUserInfo(request)) != null) {
			result.put("userInfo", userInfo);
			result.put("status", "success");
			return result;
		}
		
		result.put("status", "failed");
		return result;
	}
	

}