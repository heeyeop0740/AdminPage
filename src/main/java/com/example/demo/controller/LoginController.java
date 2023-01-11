package com.example.demo.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Doctor;
import com.example.demo.service.LoginService;
import com.example.demo.service.UserService;


@RestController
public class LoginController {
	
	@Autowired
	LoginService loginService;

	@ExceptionHandler(NullPointerException.class)
	public Map<String, Object> loginCatcher() {
		Map<String, Object> result = new HashMap<>();
		result.put("status", "failed");
		return result;
	}

	@PostMapping(value="/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> loginJ(@RequestBody Doctor user) throws NullPointerException {
		// TODO failed시 error code 변경
		
		Map<String, Object> result = new HashMap<>();
		
		Doctor doctor = loginService.getDoctorById(user);
				
		if (loginService.validateDoctor(doctor, user)) {
			result.put("token", loginService.createToken(doctor));
			result.put("status", "success");
			return result;
		}

		result.put("status", "failed");
		return result;
	}

	@PostMapping(value="/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Map<String, Object> loginX(Doctor user) throws NullPointerException {
		// TODO failed시 error code 변경
		
		Map<String, Object> result = new HashMap<>();

		Doctor doctor = loginService.getDoctorById(user);

		if (loginService.validateDoctor(doctor, user)) {
			result.put("token", loginService.createToken(doctor));
			result.put("status", "success");
			return result;
		}

		result.put("status", "failed");
		return result;
	}
	
	@PostMapping("/register")
	public Map<String, Object> register(Doctor user) {
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
