package com.bfd.smartqa.ws.serverholder;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bfd.smartqa.util.UtilHelper;

public class Holder {
	
	private static HashMap<String,ChannelPo> channels = new HashMap<String,ChannelPo>();
	private static HashMap<String,SessionPo> session = new HashMap<String,SessionPo>();
	private static HashMap<String,List<String>> uid_channel_map = new HashMap<String,List<String>>();
	
	public static ChannelPo addChannel(String key, ChannelPo channelPo)
	{
		return Holder.channels.put(key, channelPo);
	}
	
	public static ChannelPo getChannal(String key)
	{
		return Holder.channels.get(key);
	}
	
	public static ChannelPo removeChannel(String key)
	{
		return Holder.channels.remove(key);
	}
	
	public static SessionPo addSession(String key,SessionPo sessionPo)
	{
		return Holder.session.put(key, sessionPo);
	}
	
	public static SessionPo removeSession(String key)
	{
		return Holder.session.remove(key);
	}
	public static SessionPo getSession(String token)
	{
		
		SessionPo spo = Holder.session.get(token);
		if(spo != null)
		{
			spo.setAccess_time(UtilHelper.getLinuxTimeStamp());
		}
		return spo;
	}
	
	
	public static void addUidChannel(String uid,String channelCode)
	{
		if(Holder.uid_channel_map.containsKey(uid))
		{
			Holder.uid_channel_map.get(uid).add(channelCode);
		}else{
			List<String> channelList = new ArrayList<String>();
			channelList.add(channelCode);
			Holder.uid_channel_map.put(uid, channelList);
		}
	}
	
	public static void removeUidChannel(String uid,String channelCode)
	{
		if(Holder.uid_channel_map.containsKey(uid))
		{
			Holder.uid_channel_map.get(uid).remove(channelCode);
		}
	}
	
	public static List<String> getChannelByUid(String uid)
	{
		return Holder.uid_channel_map.get(uid);
	}
	
	public static boolean online(String uid)
	{
		return Holder.uid_channel_map.containsKey(uid);
	}
	
	
	
	/*
	 * 验证用户ser_key的有效性
	 */
	public static boolean validToken(Channel channel,String token)
	{
		//首先判断token和channel的对应关系
		if(token != UtilHelper.hashCode(channel))
		{
			return false;
		}
		//判断是否有此 token的session
		if(Holder.session.containsKey(token))
		{
			return true;
		}
		return false;
	}

	

}
