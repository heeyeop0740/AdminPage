package com.example.demo.service;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.PsctCode;
import com.example.demo.model.User;
import com.example.demo.model.UserInfo;

@Service
public class UserService {
	
	@Autowired
	UserMapper userMapper;
	
	public UserInfo getUserInfo(HttpServletRequest request) {
		// TODO 데이터베이스에 한번만 접근하여 결과를 구할 수 있는 방법을 모색할 필요 있음.
			
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
	
	public String getCode() {
		
		Random random = new Random();
		// 랜덤으로 0 ~ 255까지의 수 6개를 byte배열에 집어넣는다.
		byte[] code = new byte[6];
		for(int i = 0; i < code.length; i++) {
			code[i] = (byte) random.nextInt(256);
		}
		// 해당 배열을 Base64 인코딩 진행한 String 리턴
		return Base64.encodeBase64String(code);
	}
	
	/**
	 * DB내에 인자로 받은 코드와 동일한 코드가 존재하는지 확인
	 * @param psctCode
	 * @return true/false
	 */
	public boolean isSame(String psctCode) {
		// userMapper로 psctCode가 DB가 있는지 확인
		PsctCode code = userMapper.getSameCode(psctCode);
		
		// getSameCode() 메서드의 작동 이후 중복되는 코드가 존재하지 않을 때 PsctCode 의 id는  
		if(code == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * staff의 id, instId와 페이지에 표시할 user 수를 입력하여 그만큼의 user list를 반환받는다.
	 * @param id
	 * @param instId
	 * @param pageNum
	 * @return user list
	 */
	public List<User> getUsers(int id, int instId, int pageNum) {
		// TODO userMapper.getUsers에는 시작하는 offset을 넣어준다. 해당 과정에서 하드코딩된 부분(한페이지에 들어가는 사이즈, 10)을 어떻게 해결할지 생각
		return userMapper.getUsers(id, instId, (pageNum - 1) * 10);
	}
	
	public int registerUser(PsctCode psctCode) {
		return userMapper.registerUser(psctCode);
	}
	
	public User getUserByCode(String psctCode) {
		return userMapper.getUserByCode(psctCode);
	}

	public int updateUser(PsctCode psctCode) {
		return userMapper.updateUser(psctCode);
	}
	
	public PsctCode getPsctCodeById(int id, int instId, int stffId) {
		return userMapper.getPsctCodeById(id, instId, stffId);
	}
	
	public int deletePsctCodeById(int[] id) {
		
		for(int i = 0; i < id.length; i++) {
			if(userMapper.deletePsctCodeById(id[i]) == 0) {
				return 0;
			}
		}
		return 1;
	}
	
}
