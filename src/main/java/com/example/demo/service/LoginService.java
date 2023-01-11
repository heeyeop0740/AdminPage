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
	 * 문자열을 바탕으로 인코딩하여 토큰 생성을 위한 Key 생성
	 * @param keyString
	 * @return Key
	 */
	public Key getKey(String keyString) {
		
		byte[] encodedKey = Decoders.BASE64.decode(keyString);
		return new SecretKeySpec(encodedKey, "HmacSHA256");
	}
	
	/**
	 * 데이터베이스에서 가져온 admin의 정보의 일부와 만료기간을 토큰내에 첨부하여 생성
	 * @param admin
	 * @return token(String)
	 */
	public String createToken(Doctor admin) {

		Key key = getKey(keyString);
		
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR, 1);
		long exp = now.getTimeInMillis();
		
		return Jwts.builder()
				.setSubject("staff")
				.claim("name", admin.getName())
				.claim("id", String.valueOf(admin.getId()))
				.claim("instId", String.valueOf(admin.getInstId()))
				.setExpiration(new Date(exp))
				.signWith(key)
				.compact();
	}
	
	/**
	 * 토큰의 "staff" 권한 확인 및, 토큰 만료 여부 확인, 올바른 토큰 확인
	 * @param request
	 * @param key
	 * @return true or false
	 * @throws JwtException
	 */
	public boolean validateToken(HttpServletRequest request, Key key) throws JwtException{
		// TODO 토큰의 유효성 검사를 할 필요가 있는지 모르겠음. 만료 토큰에 대한 예외는 자동으로 발생함.
		
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
		
		return Integer.valueOf((String)jws.getBody().get("id"));
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
		
		return Integer.valueOf((String)jws.getBody().get("instId"));
	}
}
