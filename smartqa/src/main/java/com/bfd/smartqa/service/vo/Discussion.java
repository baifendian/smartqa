package com.bfd.smartqa.service.vo;

import java.util.List;
import java.util.Map;

public class Discussion {
	private String id;
	private String subject;
	private List<Map<String,String>> content;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<Map<String,String>> getContent() {
		return content;
	}
	
	public void setContent(List<Map<String,String>> content) {
		this.content = content;
	}

}
