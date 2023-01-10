package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.UserInfo;

@Service
public class UserService {
	
	@Autowired
	UserMapper userMapper;
	
	public UserInfo getUserInfo(HttpServletRequest request) {
	
		UserInfo userInfo = userMapper.getUserInfo((int)request.getAttribute("id"), (int)request.getAttribute("instId"));
		userInfo.setCount(userMapper.getUserNumber((int)request.getAttribute("id"), (int)request.getAttribute("instId")));

		return userInfo;
	}

}
