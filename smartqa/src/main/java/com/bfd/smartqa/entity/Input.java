package com.bfd.smartqa.entity;

import java.util.HashMap;
import java.util.Map;

public class Input {
	private String token;
	private String method;
	private Map<String, String> data;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = new HashMap<String, String>();
		for (String key : data.keySet()) {
			this.data.put(key, String.valueOf(data.get(key)));
		}
	}

}
