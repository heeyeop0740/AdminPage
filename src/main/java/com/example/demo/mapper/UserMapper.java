package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.PsctCode;

@Mapper
public interface UserMapper {
	
	public int getUserInfo(int id, int instId, String type);
	
	public int getUserNumber(int id, int instId);
	
	public PsctCode getSameCode(String psctCode);
}
