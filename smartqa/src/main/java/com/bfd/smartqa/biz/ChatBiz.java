package com.bfd.smartqa.biz;

import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bfd.smartqa.GlobalHolder;
import com.bfd.smartqa.dao.po.DiscussionPo;
import com.bfd.smartqa.entity.MsgCode;
import com.bfd.smartqa.entity.Output;
import com.bfd.smartqa.service.ChatService;
import com.bfd.smartqa.service.vo.ChatRoom;
import com.bfd.smartqa.util.JsonHelper;
import com.bfd.smartqa.util.UtilHelper;
import com.bfd.smartqa.ws.serverholder.Holder;

public class ChatBiz extends BaseBiz {
	
	public static Map<String,ChatRoom> chatRooms = new HashMap<String,ChatRoom>();
	
	private ChatService chatService = null;
	
	public ChatBiz()
	{
		this.chatService = new ChatService();
	}

	public void chat(NioSocketChannel incoming, String token, HashMap<String, String> param) {

		Output output = new Output();
		output.setMethod("chat");

		//System.out.println("in chat");
		output.setCode(MsgCode.SUCCES.getCode());
		Map<String, String> data = new HashMap<String, String>();
		data.put("msg", param.get("msg"));
		data.put("isme", "1");
		data.put("sendername", Holder.getSession(token).getChinese_name());
		data.put("roomid",param.get("roomid"));
		output.setData(data);
		output.setMsg("发送成功");
		incoming.writeAndFlush(new TextWebSocketFrame(JsonHelper
				.paserOutputObj(output)));

		
		// 给聊天室其他成员发送信息
		Output output_others = new Output();
		output_others.setMethod("chat");
		output_others.setCode(MsgCode.SUCCES.getCode());
		Map<String, String> data_others = new HashMap<String, String>();
		data_others.put("msg", param.get("msg"));
		data_others.put("isme", "0");
		data_others.put("sendername", Holder.getSession(token).getChinese_name());
		data_others.put("roomid",param.get("roomid"));
		output_others.setData(data_others);
		output_others.setMsg("接受他人信息成功");
		String speaker_id = Holder.getSession(token).getUser_id();
		String room_id = param.get("roomid");
		this.sendToOthers(room_id,speaker_id,output_others);
		
		//保存聊天信息
		Map<String,String> content = new HashMap<String,String>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		content.put("Uid", speaker_id);
		content.put("Content", param.get("msg"));
		content.put("Time", df.format(new Date()));
		ChatBiz.chatRooms.get(room_id).getContent().add(content);
	}
	
	public void createChatRoom(NioSocketChannel incoming, String token, HashMap<String, String> param)
	{
		
		String creator_id = Holder.getSession(token).getUser_id();
		String roomID = creator_id+"_" + String.valueOf(UtilHelper.getLinuxTimeStamp());
		GlobalHolder.getLogger().debug("create chat room creator_id:"+ creator_id + "roomid:"+roomID);
		String[] user_id_list = param.get("user_ids").split(",");
		List<String> userList = new ArrayList<String>();
		for(String user_id:user_id_list)
		{
			userList.add(user_id.split("\\|")[0]);
		}
		
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setRoomId(roomID);
		chatRoom.setSubject(param.get("subject"));
		chatRoom.setUserIds(userList);
		List<String> tags = GlobalHolder.getSegmenter().process(param.get("subject"));
		chatRoom.setCaptionInTag(UtilHelper.listToString(tags, ","));
		chatRoom.setName(roomID);
		chatRoom.setCreator(Integer.valueOf(creator_id));
		chatRoom.getUserIds().add(creator_id);
		ChatBiz.chatRooms.put(roomID, chatRoom);
		
		
		//持久化到数据库中
		int rid = this.chatService.createChatRoom(chatRoom);
		
		//根据user id list 判断用户是否在线
		Set<String> uids_in_rooms = new HashSet<String>();
		
		for(String user_id:user_id_list)
		{	
			String[] id_name = user_id.split("\\|");
			uids_in_rooms.add(id_name[0]+"[|]"+ id_name[1]+"[|]"+(Holder.online(id_name[0])?"1":"0"));
		}
		//自己进入chatlist
		uids_in_rooms.add(creator_id+"[|]"+ Holder.getSession(token).getChinese_name() + "[|]1" );
		
		String userListStr = "";
		for(String item:uids_in_rooms)
		{
			userListStr = userListStr + item+"[,]";
		}
		
		if(userListStr.length() > 0)
		{
			userListStr = userListStr.substring(0, userListStr.length() - 3);
		}
		
		
		//该连接channel上返回创建OK
		Output output = new Output();
		output.setMethod("createchatroom");
		output.setCode(MsgCode.SUCCES.getCode());
		Map<String, String> data = new HashMap<String, String>();
		data.put("result", "0");
		data.put("userlist", userListStr);
		//data.put("userlist", "1[|]刘敬斌[|]0[,]2[|]邝俊伟[|]1");
		data.put("roomname", roomID);
		data.put("roomid", roomID);
		data.put("onwer", "1");
		data.put("subject", param.get("subject"));
		output.setData(data);
		output.setMsg("创建成功");
		incoming.writeAndFlush(new TextWebSocketFrame(JsonHelper
				.paserOutputObj(output)));
		
		//其他相关channel返回被拉入新聊天室信息
		Output output_others = new Output();
		output_others.setMethod("joinchatroom");
		output_others.setCode(MsgCode.SUCCES.getCode());
		Map<String, String> data_others = new HashMap<String, String>();
		data_others.put("result", "0");
		data_others.put("userlist", userListStr);
		data_others.put("roomname", roomID);
		data_others.put("roomid", roomID);
		data_others.put("onwer", "0");
		data_others.put("subject", param.get("subject"));
		data_others.put("msg", "您被拉进\""+param.get("subject")+"\"主题的聊天室");
		output_others.setData(data_others);
		output_others.setMsg("您被拉进\""+param.get("subject")+"\"主题的聊天室");
		this.sendToOthers(roomID,creator_id,output_others);
		
		
	}
	
	public void closeChatRoom(NioSocketChannel incoming, String token, HashMap<String, String> param) {
		//持久化聊天信息
		String RoomID = param.get("roomid");
		GlobalHolder.getLogger().debug("closeChatRoom "+ RoomID);
		ChatRoom chatRoom = ChatBiz.chatRooms.get(RoomID);
		DiscussionPo discussionPo = this.chatService.getDiscussionByName(chatRoom.getName());
		String contentJson = JsonHelper.parseContentJson(chatRoom.getContent());
		discussionPo.setContent(contentJson);
		this.chatService.setContentOfDiscussion(discussionPo);

		// 给关闭者发送信息
		Output output = new Output();
		output.setMethod("close");
		System.out.println("in closing");
		output.setCode(MsgCode.SUCCES.getCode());
		output.setMsg("关闭成功");
		Map<String, String> data = new HashMap<String, String>();
		data.put("roomid", param.get("roomid"));
		data.put("msg", "关闭成功");
		output.setData(data);
		incoming.writeAndFlush(new TextWebSocketFrame(JsonHelper
				.paserOutputObj(output)));

		// 给聊天室其他成员发送信息
		Output output_others = new Output();
		output_others.setMethod("close");
		output_others.setCode(MsgCode.SUCCES.getCode());
		output_others.setMsg("聊天室被创建者关闭");
		Map<String, String> data_others = new HashMap<String, String>();
		data_others.put("roomid", param.get("roomid"));
		data_others.put("msg", "聊天室被创建者关闭");
		output_others.setData(data_others);
		String speaker_id = Holder.getSession(token).getUser_id();
		String room_id = param.get("roomid");
		this.sendToOthers(room_id, speaker_id, output_others);
	}
	
	private void sendToOthers(String roomId,String speaker_user_id,Output output)
	{
		List<String> userInRoom = ChatBiz.chatRooms.get(roomId).getUserIds();
		TextWebSocketFrame sendObj = new TextWebSocketFrame(JsonHelper.paserOutputObj(output));
		for(String user_id : userInRoom)
		{
			System.out.println(user_id);
			if(!user_id.equals(speaker_user_id))
			{
				List<String> channel_code_list = Holder.getChannelByUid(user_id);
				if(channel_code_list == null)
				{
					//该用户不在线，发送邮件进行通知
					continue;
				}
				for(String channelcode:channel_code_list)
				{
					System.out.println("-->\n" + sendObj + "\n<--");
					Holder.getChannal(channelcode).getChannel().writeAndFlush(sendObj);
				}
			}
		}
	}

	
}
