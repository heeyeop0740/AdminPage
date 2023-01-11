package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserInfo;
import com.example.demo.service.UserService;

@CrossOrigin
@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	

	@GetMapping("/userInfo")
	public Map<String, Object> getUserInfo(HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<>();
		
		UserInfo userInfo = userService.getUserInfo(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        ResponseEntity<String> response = new ResponseEntity<String>("hello",headers, HttpStatus.OK);
		
		result.put("userInfo", userInfo);
		result.put("status", "success");

		return result;
	}

}