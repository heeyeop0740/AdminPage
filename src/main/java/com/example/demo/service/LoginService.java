package com.example.demo.service;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.DoctorMapper;
import com.example.demo.model.Doctor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;

@Service
public class LoginService {

	
	public static String keyString = "We8zEU/7F++IhZ/PLhJWXLRtHrcPLn79UR1Pe1MUmfo=";
	
	@Autowired
	DoctorMapper doctorMapper;
	
	public Doctor getDoctorById(Doctor doctor) {
		return doctorMapper.getDoctorById(doctor);
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int registerDoctor(Doctor doctor) {
		return doctorMapper.registerDoctor(doctor);
	}
	
	public boolean validateDoctor(Doctor admin, Doctor user) {
		
		if (admin.getPwd().equals(user.getPwd())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 */
	public Key getKey(String keyString) {
		//[TODO]
		byte[] encodedKey = Decoders.BASE64.decode(keyString);
		return new SecretKeySpec(encodedKey, "HmacSHA256");
	}
	
	public String createToken(Doctor admin) {

		Key key = getKey(keyString);
		
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR, 1);
		long exp = now.getTimeInMillis();
		
		return Jwts.builder()
				.setSubject("staff")
				.claim("id", String.valueOf(admin.getId()))
				.claim("instId", String.valueOf(admin.getInstId()))
				.setExpiration(new Date(exp))
				.signWith(key)
				.compact();
	}
	
	public boolean validateToken(HttpServletRequest request, Key key) throws JwtException{
		
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String token ="";
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			token = authorizationHeader.substring("Bearer ".length());
		}
				
		Jws<Claims> jws = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token);
			
			if (jws.getBody().getSubject().equals("staff") && (new Date().compareTo(jws.getBody().getExpiration()) <= 0)) {
				
				return true;
			}
			return false;
	}
	
	
	/**
	 * request의 Authorization Header에서 token 부분만을 추출
	 * @param request
	 * @return token
	 */
	public String getToken(HttpServletRequest request) {
		
		return request.getHeader("authorization").substring("Bearer ".length());
		
	}
	
	/**
	 * filter에서 토큰 유효성 확인 후 토큰에서 stffId 추출
	 * @param request
	 * @return stffId
	 */
	public int getId(String token) {
		Jws<Claims> jws = Jwts.parserBuilder()
				.setSigningKey(getKey(keyString))
				.build()
				.parseClaimsJws(token);
		
		return (int)jws.getBody().get("id");
	}
	
	/**
	 * filter에서 토큰 유효성 확인 후 토큰에서 instId 추출
	 * @param request
	 * @return instId
	 */
	public int getInstId(String token) {
		Jws<Claims> jws = Jwts.parserBuilder()
				.setSigningKey(getKey(keyString))
				.build()
				.parseClaimsJws(token);
		
		return (int)jws.getBody().get("instId");
	}
}
