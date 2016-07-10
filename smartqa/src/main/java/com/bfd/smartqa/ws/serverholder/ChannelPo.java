package com.bfd.smartqa.ws.serverholder;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ChannelPo {
	private String user_id;
	private String ser_key;
	private Channel channel;
	
	public ChannelPo(String user_id,String ser_key,Channel channel)
	{
		this.user_id = user_id;
		this.ser_key = ser_key;
		this.channel = channel;
	}
	

	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getSer_key() {
		return ser_key;
	}
	public void setSer_key(String ser_key) {
		this.ser_key = ser_key;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	

}
