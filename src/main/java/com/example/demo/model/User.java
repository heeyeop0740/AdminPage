package com.example.demo.model;

public class User {

	private int id;
	
	private String uid;
	
	private String name;
	
	private String psctCode;
	
	private String termsAgree;
	
	private String status;
	
	private String profileImage;
	
	private String trainingPath;
	
	private boolean appGuideYN;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPsctCode() {
		return psctCode;
	}

	public void setPsctCode(String psctCode) {
		this.psctCode = psctCode;
	}

	public String getTermsAgree() {
		return termsAgree;
	}

	public void setTermsAgree(String termsAgree) {
		this.termsAgree = termsAgree;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getTrainingPath() {
		return trainingPath;
	}

	public void setTrainingPath(String trainingPath) {
		this.trainingPath = trainingPath;
	}

	public boolean isAppGuideYN() {
		return appGuideYN;
	}

	public void setAppGuideYN(boolean appGuideYN) {
		this.appGuideYN = appGuideYN;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", uid=" + uid + ", name=" + name + ", psctCode=" + psctCode + ", termsAgree="
				+ termsAgree + ", status=" + status + ", profileImage=" + profileImage + ", trainingPath="
				+ trainingPath + ", appGuideYN=" + appGuideYN + "]";
	}	
}
