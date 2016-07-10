package com.bfd.smartqa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

public class Test {

	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("haha", "test");
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		list.add(map);
		
		JSONArray obj = JSONArray.fromObject(list);
		System.out.println(obj.toString());
	}
}
