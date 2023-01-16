package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.PsctCode;
import com.example.demo.model.User;

@Mapper
public interface UserMapper {
	
	public int getUserInfo(int id, int instId, String type);
	
	public int getUserNumber(int id, int instId);
	
	public PsctCode getSameCode(String psctCode);
	
	public List<User> getAllUsers(int id, int instId, int pageNum);
	
	public List<User> getUsers(int id, int instId, int startOffset);
	
	public int registerUser(PsctCode psctCode);
	
	public User getUserByCode(String psctCode);

	public int updateUser(PsctCode psctCode);
	
	public PsctCode getPsctCodeById(int id, int instId, int stffId);
	
	public int deletePsctCodeById(int id);
}