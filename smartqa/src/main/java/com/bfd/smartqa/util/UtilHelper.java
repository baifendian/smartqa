package com.bfd.smartqa.util;

import java.util.List;
import java.util.UUID;

public class UtilHelper {

	public static String hashCode(Object obj) {
		return String.valueOf(obj.hashCode() & 0x7FFFFFFF);
	}
	
	/*
	 * 返回linux时间戳
	 */
	public static long getLinuxTimeStamp()
	{
		return System.currentTimeMillis(); 
	}
	
	/*
	 * 返回guid
	 */
	public static String createUUID()
	{
		UUID uuid  =  UUID.randomUUID(); 
		return UUID.randomUUID().toString();
	}
	
	/*
	 * 将list转变为String
	 */
	public static String listToString(List<String> list, String string) 
	{    
		StringBuilder sb = new StringBuilder();     
		for (int i = 0; i < list.size(); i++) {        
			sb.append(list.get(i));        
			if (i < list.size() - 1) {            
				sb.append(string);        
				}    
			}    
		return sb.toString();
	}
	
}

