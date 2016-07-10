package com.bfd.smartqa.biz;

import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bfd.smartqa.GlobalHolder;
import com.bfd.smartqa.entity.MsgCode;
import com.bfd.smartqa.entity.Output;
import com.bfd.smartqa.service.RecommendService;
import com.bfd.smartqa.service.vo.Discussion;
import com.bfd.smartqa.service.vo.User;
import com.bfd.smartqa.util.JsonHelper;
import com.bfd.smartqa.ws.serverholder.Holder;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

public class RecommendBiz extends BaseBiz {

	private RecommendService recommendService;

	public RecommendBiz() {
		this.recommendService = new RecommendService();
	}

	/*
	 * 根据对应的主题，搜索推荐的人员
	 */
	public void recommendUser(NioSocketChannel incoming, String token,
			HashMap<String, String> param) {
		// 根据主题进行分词，推出标签
		String subject = param.get("subject");
		List<String> tagList = GlobalHolder.getSegmenter().process(subject);

		// 访问service，根据标签推出相关的用户
		List<User> recommmendList = this.recommendService
				.recommendUser(tagList);
		// channnel 进行信息的返回
		Output output = new Output();
		output.setMethod("recommenduser");
		output.setCode(MsgCode.SUCCES.getCode());
		Map<String, String> data = new HashMap<String, String>();
		String userListStr = "";
		String master_uid = Holder.getSession(token).getUser_id();
		for (User user : recommmendList) {
			//去掉自己的id
			if(master_uid.equals(user.getUserId()))
			{
				continue;
			}
			userListStr = userListStr + user.getUserId() + "[|]"
					+ user.getChineseName() + "[|]" + user.getScore()
					+ "[,]";
		}
		if(userListStr.length() > 0)
		{
			userListStr = userListStr.substring(0, userListStr.length() - 3);
		}
		data.put("userlist", userListStr);
		output.setData(data);
		output.setMsg("获取推荐用户成功");
		incoming.writeAndFlush(new TextWebSocketFrame(JsonHelper
				.paserOutputObj(output)));

	}

	/*
	 * 根据对应的主题，搜索曾经进行过的聊天沟通
	 */
	public void recommendDiscussion(NioSocketChannel incoming, String token,
			HashMap<String, String> param) {
		// 根据主题进行分词，推出标签
		String subject = param.get("subject");
		List<String> tagList = GlobalHolder.getSegmenter().process(subject);

		// 访问service，根据标签推出相关的讨论话题
		List<Discussion> recommmendList = this.recommendService
				.recommendDiscussion(tagList);
		// channnel 进行信息的返回
		Output output = new Output();
		output.setMethod("recommenddiscussion");
		output.setCode(MsgCode.SUCCES.getCode());
		Map<String, String> data = new HashMap<String, String>();
		String disListStr = "";
		for (Discussion dis : recommmendList) {
			disListStr = disListStr + dis.getId() + "[|]"
					+ dis.getSubject()+ "[,]";
		}
		if(disListStr.length() > 0)
		{
			disListStr = disListStr.substring(0, disListStr.length() - 3);
		}
		data.put("discussionlist", disListStr);
		output.setData(data);
		output.setMsg("获取推荐话题成功");
		// channnel 进行信息的返回
		incoming.writeAndFlush(new TextWebSocketFrame(JsonHelper
				.paserOutputObj(output)));		

	}

}
