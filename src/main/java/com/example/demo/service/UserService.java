package com.example.demo.service;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.PsctCode;
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
	
	public String getCode() {
		
		Random random = new Random();
		// 랜덤으로 0 ~ 256까지의 수 6개를 byte배열에 집어넣는다.
		byte[] code = new byte[6];
		for(int i = 0; i < code.length; i++) {
			code[i] = (byte) random.nextInt(128);
		}
		// 해당 배열을 Base64 인코딩 진행한 String 리턴
		return Base64.encodeBase64String(code);
	}
	
	public boolean isSame(String psctCode) {
		
		// userMapper로 psctCode가 DB가 있는지 확인
		PsctCode code = userMapper.getSameCode(psctCode);
		System.out.println(code);
		
		// getSameCode() 메서드의 작동 이후 중복되는 코드가 존재하지 않을 때 PsctCode 의 id는  
		if(code == null) {
			return false;
		}
		return true;
	}

}
