package com.bfd.smartqa.biz;

import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.bfd.smartqa.entity.MsgCode;
import com.bfd.smartqa.entity.Output;
import com.bfd.smartqa.service.UserService;
import com.bfd.smartqa.service.vo.User;
import com.bfd.smartqa.util.JsonHelper;
import com.bfd.smartqa.util.UtilHelper;
import com.bfd.smartqa.ws.serverholder.ChannelPo;
import com.bfd.smartqa.ws.serverholder.Holder;
import com.bfd.smartqa.ws.serverholder.SessionPo;

public class LoginBiz extends BaseBiz {

	private UserService userService = null;

	public LoginBiz() {
		this.userService = new UserService();
	}

	public void login(NioSocketChannel incoming, String tokenstr,
			HashMap<String, String> param) {

		User user = this.userService.valid(param.get("username"),
				param.get("password"));
		Output output = new Output();
		output.setMethod("login");
		if (user != null) {
			output.setCode(MsgCode.SUCCES.getCode());
			Map<String, String> data = new HashMap<String, String>();
			data.put("login", "0");
			data.put("chinesename", user.getChineseName());
			String channel_code = UtilHelper.hashCode(incoming);
			String token = DigestUtils.md5Hex(channel_code);
			data.put("token", token);
			output.setData(data);
			output.setMsg("登录成功");
			// 登录成功的情况下，需要修改server holder相关的
			ChannelPo channelPo = new ChannelPo(user.getUserId(), token,
					incoming);
			Holder.addChannel(channel_code, channelPo);

			Holder.addUidChannel(user.getUserId(), channel_code);

			SessionPo sessionPo = new SessionPo();
			sessionPo.setChannel_code(channel_code);
			sessionPo.setChinese_name(user.getChineseName());
			sessionPo.setUser_id(user.getUserId());
			sessionPo.setAccess_time(UtilHelper.getLinuxTimeStamp());
			Holder.addSession(token, sessionPo);

			incoming.writeAndFlush(new TextWebSocketFrame(JsonHelper
					.paserOutputObj(output)));

		} else {
			output.setCode(MsgCode.SUCCES.getCode());
			Map<String, String> data = new HashMap<String, String>();
			data.put("login", "1");
			output.setData(data);
			output.setMsg("登录失败");

			incoming.writeAndFlush(new TextWebSocketFrame(JsonHelper
					.paserOutputObj(output)));
			incoming.close(); // 登录失败连接断开
		}

	}

}
