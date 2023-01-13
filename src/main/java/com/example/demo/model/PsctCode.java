package com.example.demo.model;

public class PsctCode {

	@Override
	public String toString() {
		return "PsctCode [psctCode=" + psctCode + ", id=" + id + ", instId=" + instId + ", stffId=" + stffId + ", name="
				+ name + ", uId=" + uId + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

	private String psctCode;
	
	private int id;
	
	private int instId;
	
	private int stffId;
	
	private String name;
	
	private String uId;
	
	private String startDate;
	
	private String endDate;

	public String getPsctCode() {
		return psctCode;
	}

	public void setPsctCode(String psctCode) {
		this.psctCode = psctCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInstId() {
		return instId;
	}

	public void setIsntId(int isntId) {
		this.instId = isntId;
	}

	public int getStffId() {
		return stffId;
	}

	public void setStffId(int stffId) {
		this.stffId = stffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
