package com.bfd.smartqa.service.vo;

public class User {

	private String chineseName;
	private String userId;
	private int score;
	
	public User(String userId,String chineseName)
	{
		this.userId = userId;
		this.chineseName = chineseName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
