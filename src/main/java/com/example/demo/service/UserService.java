package com.example.demo.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.UserInfo;

@Service
public class UserService {
	
	@Autowired
	UserMapper userMapper;
	
	public UserInfo getUserInfo(HttpServletRequest request) {
			
		UserInfo userInfo = new UserInfo();
		
		userInfo.setDfCount(userMapper.getUserInfo((int)request.getAttribute("id"), (int)request.getAttribute("instId"), "DF"));
		userInfo.setHnCount(userMapper.getUserInfo((int)request.getAttribute("id"), (int)request.getAttribute("instId"), "HN"));
		userInfo.setHrCount(userMapper.getUserInfo((int)request.getAttribute("id"), (int)request.getAttribute("instId"), "HR"));
		userInfo.setLnCount(userMapper.getUserInfo((int)request.getAttribute("id"), (int)request.getAttribute("instId"), "LN"));
		userInfo.setLrCount(userMapper.getUserInfo((int)request.getAttribute("id"), (int)request.getAttribute("instId"), "LR"));
//		userInfo.setNullCount(userMapper.getUserInfo((int)request.getAttribute("id"), (int)request.getAttribute("instId"), "NOTNULL"));
		userInfo.setCount(userMapper.getUserNumber((int)request.getAttribute("id"), (int)request.getAttribute("instId")));
		userInfo.setNullCount(userInfo.getCount() - (userInfo.getDfCount() + userInfo.getHnCount() + userInfo.getHrCount() + userInfo.getLnCount() + userInfo.getLrCount()));
		
		return userInfo;
	}

}
