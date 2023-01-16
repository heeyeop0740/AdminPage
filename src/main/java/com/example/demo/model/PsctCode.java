package com.example.demo.model;

import java.util.Date;

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
	
	private Date startDate;
	
	private Date endDate;

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

	public void setInstId(int isntId) {
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public void updatePsctCode(PsctCode psctCode) {
		if(psctCode.getPsctCode() != null)
			this.setPsctCode(psctCode.getPsctCode());
		if(psctCode.getInstId() != 0) 
			this.setInstId(psctCode.getId());
		if(psctCode.getStffId() != 0)
			this.setStffId(psctCode.getStffId());
		if(psctCode.getName() != null)
			this.setName(psctCode.getName());
		if(psctCode.getuId() != null)
			this.setuId(psctCode.getuId());
		if(psctCode.getStartDate() != null)
			this.setStartDate(psctCode.getStartDate());
		if(psctCode.getEndDate() != null)
			this.setEndDate(psctCode.getEndDate());
	}
	
}
