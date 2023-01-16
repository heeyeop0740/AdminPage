package com.example.demo.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Doctor;
import com.example.demo.service.LoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="관리자", description = "로그인 API")
public class LoginController {
	
	@Autowired
	LoginService loginService;

	@ExceptionHandler(NullPointerException.class)
	public Map<String, Object> loginCatcher(Exception ex) {
		Map<String, Object> result = new HashMap<>();
		result.put("message", ex.getMessage());
		result.put("status", "failed");
		return result;
	}

	@PostMapping(value="/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "로그인", description = "관리자 로그인")
	public Map<String, Object> loginJ(@RequestBody Doctor user) {
		// TODO failed시 error code 변경
		
		Map<String, Object> result = new HashMap<>();
		
		Doctor doctor = loginService.getDoctorByUserId(user);
				
		if (loginService.validateDoctor(doctor, user)) {
			result.put("accessToken", loginService.createAcessToken(doctor, loginService.getAcessTokenKeyString()));
			result.put("refreshToken", loginService.createRefreshToken(doctor, loginService.getRefreshTokenKeyString()));
			result.put("status", "success");
			return result;
		}

		result.put("status", "failed");
		return result;
	}

	@PostMapping(value="/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//	@Operation(summary = "로그인", description = "관리자 로그인")
	public Map<String, Object> loginX(Doctor user) throws NullPointerException {
		// TODO failed시 error code 변경
		
		Map<String, Object> result = new HashMap<>();

		Doctor doctor = loginService.getDoctorByUserId(user);

		if (loginService.validateDoctor(doctor, user)) {
			result.put("accessToken", loginService.createAcessToken(doctor, loginService.getAcessTokenKeyString()));
			result.put("refreshToken", loginService.createRefreshToken(doctor, loginService.getRefreshTokenKeyString()));
			result.put("status", "success");
			return result;
		}

		result.put("status", "failed");
		return result;
	}
	
	@GetMapping("/login/refresh")
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "로그인", description = "리프레시 토큰을 이용한 관리자 로그인")
	public Map<String, Object> loginRefreshToken(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		
		Doctor doctor = loginService.getDoctorById((int)request.getAttribute("id"));
		
		result.put("accessToken", loginService.createAcessToken(doctor, loginService.getAcessTokenKeyString()));
		result.put("status", "success");
		
		return result;
	}
		
	@PostMapping("/register")
	@Operation(summary = "관리자 등록", description = "관리자 등록")
	public Map<String, Object> register(@RequestBody Doctor user) {
		// TODO failed시 error code 변경
		
		Map<String, Object> result = new HashMap<>();
		
		if(loginService.registerDoctor(user) == 0) {
			result.put("status", "failed");
			return result;
		}
		
		result.put("status", "success");
		return result;
	}
}
