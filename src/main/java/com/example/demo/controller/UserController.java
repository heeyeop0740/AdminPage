package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserInfo;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	UserService userService;
	

	@GetMapping("/userinfo")
	public Map<String, Object> getUserInfo(HttpServletRequest request) {
		System.out.println("/userInfo");
		System.out.println(request.getHeader("origin"));
		
		Map<String, Object> result = new HashMap<>();
		UserInfo userInfo = userService.getUserInfo(request);
        
		result.put("userInfo", userInfo);
		result.put("status", "success");
		
		System.out.println(result);

		return result;
	}
	
	@PostMapping("/user/register")
	public Map<String, Object> registerUser(HttpServletRequest request, String psctCode, String Name) {
		Map<String, Object> result = new HashMap<>();
		
		// instId, id - 토큰에서 가져오는 정보 User 모델객체로 설정한다. {psct_code, user name, start_date, end_date} - 리퀘스트 바디에서 가져오는 정보
		// userService에서 제공하는 DB의 TB_PSCT_CODE 테이블에 해당 정보를 넣는다.
		
		return result;
	}
	
	@GetMapping("user/code")
	public Map<String, Object> getCode(HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<>();
		// 서비스단에서 문자열 생성
		String psctCode = userService.getCode();
		System.out.println("code : " + psctCode);
		// 서비스단에서 TB_PSCT_CODE 중복여부 확인
		while(userService.isSame(psctCode)) {
			psctCode = userService.getCode();
			System.out.println("code : " + psctCode);
		}
		
		// 중복되지 않는다면 해당 코드와 상태 리턴
		result.put("psctCode", psctCode);
		
		return result;
	}

	
	
	

}