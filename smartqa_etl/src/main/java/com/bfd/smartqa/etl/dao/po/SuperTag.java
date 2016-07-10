package com.bfd.smartqa.etl.dao.po;

public class SuperTag {

	private int ID;
	private int SuperID;
	private int TagID;
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public int getSuperID() {
		return SuperID;
	}
	
	public void setSuperID(int superID) {
		this.SuperID = superID;
	}
	
	public int getTagID() {
		return TagID;
	}
	
	public void setTagID(int TagID) {
		this.TagID = TagID;
	}
	
	public String toString() {
		return "SuperTag [id=" + ID + ", SuperId=" + SuperID + ", TagId=" + TagID + "]";
	}
}
