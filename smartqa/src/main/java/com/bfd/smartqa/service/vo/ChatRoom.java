package com.bfd.smartqa.service.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatRoom {
	private String roomId;
	private String caption;
	private List<Map<String,String>> content;
	private String captionInTag;
	private String name;
	private int creator;
	private List<String> userIds;
	
	public ChatRoom()
	{
		this.setUserIds(new ArrayList<String>());
		this.content = new ArrayList<Map<String,String>>();
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getSubject() {
		return caption;
	}

	public void setSubject(String subject) {
		this.caption = subject;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map<String,String>> getContent() {
		return content;
	}

	public void setContent(List<Map<String,String>> content) {
		this.content = content;
	}

	public String getCaptionInTag() {
		return captionInTag;
	}

	public void setCaptionInTag(String captionInTag) {
		this.captionInTag = captionInTag;
	}

	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}
	
	

}
