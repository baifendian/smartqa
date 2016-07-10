package com.bfd.smartqa.etl.dao.po;

public class SuperTagScore {

	private int ID;
	private int Uid;
	private int SuperID;
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
	
	public int getSuperID() {
		return SuperID;
	}
	
	public void setSuperID(int SuperID) {
		this.SuperID = SuperID;
	}
	
	public float getScore() {
		return Score;
	}
	
	public void setScore(float Score) {
		this.Score = Score;
	}
}
