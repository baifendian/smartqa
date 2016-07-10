package com.bfd.smartqa.biz;

import com.bfd.smartqa.entity.Input;
import com.bfd.smartqa.util.UtilHelper;
import com.bfd.smartqa.ws.serverholder.ChannelPo;
import com.bfd.smartqa.ws.serverholder.Holder;
import com.bfd.smartqa.ws.serverholder.SessionPo;

import io.netty.channel.Channel;

public class AuthAgent {

	public boolean check(Channel channel,Input msg) {

		if (msg.getToken() == null || msg.getToken().equals("")
				|| msg.getToken().equals("null")) {
			if (msg.getMethod().equals("login")) {
				return true;
			}
		} else {
			SessionPo session = Holder.getSession(msg.getToken());
			if(session != null) //session 仍然有效
			{
				//判断session和session中的channel_code是否一致
				if(session.getChannel_code().equals(UtilHelper.hashCode(channel)))
				{
					return true;
				}else{
					//处理不一致的情况，此时发生了网络异常，长连接已经断开
					if(Holder.getChannal(session.getChannel_code()) == null)
					{
						//session中的长连接链路不存在，如果存在则此请求为伪造
						Holder.addChannel(UtilHelper.hashCode(channel), new ChannelPo(session.getUser_id(),msg.getToken(),channel));
						Holder.addUidChannel(session.getUser_id(), UtilHelper.hashCode(channel));
						return true;
					}
				}
			}
		}

		return false;

	}

}
