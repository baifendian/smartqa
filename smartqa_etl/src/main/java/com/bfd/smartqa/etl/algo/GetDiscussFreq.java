package com.bfd.smartqa.etl.algo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

public class GetDiscussFreq {

	private final static String key = "Uid";
	
	public static Map<Integer,Integer> getDiscussFreq(String content) {
		Map<Integer,Integer> talkFreq = new HashMap<Integer,Integer>();
		JSONArray obj = JSONArray.fromObject(content);
		@SuppressWarnings("unchecked")
		List<Map<String,String>> parsedContent = (List<Map<String,String>>) obj;
		
		for (Map<String,String> item : parsedContent) {
			int Uid = Integer.parseInt(item.get(key));
			
			if (talkFreq.containsKey(Uid)) {
				int newVal = talkFreq.get(Uid) + 1;
				talkFreq.put(Uid, newVal);
			} else {
				talkFreq.put(Uid, 1);
			}
		}
		
		for (int key : talkFreq.keySet()) {
			System.out.println("Key:" + key);
			System.out.println("Val:" + talkFreq.get(key));
		}
		
		return talkFreq;
	}
	
}
