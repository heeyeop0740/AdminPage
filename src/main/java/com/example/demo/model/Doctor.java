package com.example.demo.model;

import lombok.Data;

@Data
public class Doctor {
	
	private int id;
	private String userId;
	private String name;
	private String pwd;
	private int instId;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setStaffId(String userId) {
		this.userId = userId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setStaffName(String name) {
		this.name = name;
	}
	
	public String getPwd() {
		return pwd;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public int getInstId() {
		return instId;
	}
	
	public void setInstId(int instId) {
		this.instId = instId;
	}
}
