package com.bfd.smartqa.biz;

import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bfd.smartqa.GlobalHolder;
import com.bfd.smartqa.dao.po.DiscussionPo;
import com.bfd.smartqa.entity.MsgCode;
import com.bfd.smartqa.entity.Output;
import com.bfd.smartqa.service.ChatService;
import com.bfd.smartqa.service.vo.ChatRoom;
import com.bfd.smartqa.service.vo.Discussion;
import com.bfd.smartqa.util.JsonHelper;

public class DiscussionBiz extends BaseBiz{
	
	
	private ChatService chatService = null;
	
	public DiscussionBiz()
	{
		this.chatService = new ChatService();
	}
	
	public void refreshHotList(NioSocketChannel incoming, String token, HashMap<String, String> param) 
	{
		GlobalHolder.getLogger().debug("token: "+token+" refreshHotList");
		Output output = new Output();
		output.setMethod("refreshhotlist");
		
		int count = Integer.valueOf(String.valueOf(param.get("count")));
		
		List<DiscussionPo> disccusionList = this.chatService.getHotList(count);
		output.setCode(MsgCode.SUCCES.getCode());
		Map<String, String> data = new HashMap<String, String>();
		String disListStr = "";
		for(DiscussionPo po : disccusionList)
		{
			disListStr = disListStr+po.getId()+"[|]"+po.getCaption()+"[,]"; 
		}
				
		data.put("disccusions", disListStr.substring(0,disListStr.length()-3));
		
		output.setData(data);
		output.setMsg("返回成功");
		incoming.writeAndFlush(new TextWebSocketFrame(JsonHelper
				.paserOutputObj(output)));
	}


	

	

}
