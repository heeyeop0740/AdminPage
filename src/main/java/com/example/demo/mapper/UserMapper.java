package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.UserInfo;

@Mapper
public interface UserMapper {
	
	public UserInfo getUserInfo(int id, int instId);
	
	public int getUserNumber(int id, int instId);
}
