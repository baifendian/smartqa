package com.bfd.smartqa.dao.po;

public class UserTagScorePo {

	private int ID;
	private int Uid;
	private int TagID;
	private float Score;
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public int getUid() {
		return Uid;
	}
	
	public void setUid(int Uid) {
		this.Uid = Uid;
	}
	
	public int getTagID() {
		return TagID;
	}
	
	public void setTagID(int TagID) {
		this.TagID = TagID;
	}
	
	public float getScore() {
		return Score;
	}
	
	public void setScore(float Score) {
		this.Score = Score;
	}
}
