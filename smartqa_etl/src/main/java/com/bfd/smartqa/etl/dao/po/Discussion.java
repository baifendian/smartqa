package com.bfd.smartqa.etl.dao.po;

public class Discussion {

	private int ID;
	private String Caption;
	private String CaptionInTag;
	private String Content;
	private int Ishandle;
	private int Status;
	private int Hot;
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public String getCaption() {
		return Caption;
	}
	
	public void setCaption(String Caption) {
		this.Caption = Caption;
	}
	
	public String getCaptionInTag() {
		return CaptionInTag;
	}
	
	public void setCaptionInTag(String CaptionInTag) {
		this.CaptionInTag = CaptionInTag;
	}
	
	public String getContent() {
		return Content;
	}
	
	public void setContent(String Content) {
		this.Content = Content;
	}
	
	public int getIshandle() {
		return Ishandle;
	}
	
	public void setIshandle(int Ishandle) {
		this.Ishandle = Ishandle;
	}
	
	public int getStatus() {
		return Status;
	}
	
	public void setStatus(int Status) {
		this.Status = Status;
	}
	
	public int getHot() {
		return Hot;
	}
	
	public void setHot(int Hot) {
		this.Hot = Hot;
	}
}
