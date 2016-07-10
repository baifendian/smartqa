package com.bfd.smartqa.util;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.bfd.smartqa.entity.Input;
import com.bfd.smartqa.entity.Output;

public class JsonHelper {

	@SuppressWarnings("unchecked")
	public static Input paserInputJson(String json) {
		System.out.println(json);
		JSONObject jb = JSONObject.fromObject(json);
		Input input = new Input();
		input.setToken(jb.getString("token"));
		input.setMethod(jb.getString("method"));
		input.setData(jb.getJSONObject("data"));
		return input;
	}

	public static String paserOutputObj(Output out) {
		JSONObject obj = JSONObject.fromObject(out);
		return obj.toString();
	}
	
	public static String parseContentJson(List<Map<String,String>> content) {
		JSONArray obj = JSONArray.fromObject(content);
		return obj.toString();
	}

}
