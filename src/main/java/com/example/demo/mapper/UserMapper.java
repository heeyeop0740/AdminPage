package com.example.demo.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
	
	public int getUserInfo(int id, int instId, String type);
	
	public int getUserNumber(int id, int instId);
}
