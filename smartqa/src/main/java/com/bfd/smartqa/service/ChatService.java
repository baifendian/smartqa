package com.bfd.smartqa.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.bfd.smartqa.GlobalHolder;
import com.bfd.smartqa.dao.inter.IDiscussionOperator;
import com.bfd.smartqa.dao.po.DiscussionPo;
import com.bfd.smartqa.service.vo.ChatRoom;
import com.bfd.smartqa.service.vo.User;

public class ChatService {

	/*
	 * 持久化聊天室的数据
	 */
	public int createChatRoom(ChatRoom room) {
		// 首先保存，聊天室主表信息
		SqlSession session = GlobalHolder.getSessionFactory().openSession();

		IDiscussionOperator discusionOperator = session
				.getMapper(IDiscussionOperator.class);
		DiscussionPo disPo = new DiscussionPo();
		disPo.setCaption(room.getSubject());
		disPo.setContent("");
		disPo.setCaptionintag(room.getCaptionInTag());
		disPo.setName(room.getName());
		disPo.setCreator(room.getCreator());
		discusionOperator.insertChatroom(disPo);

		// 保存聊天室关联用户的userid
//		discusionOperator.insertRoomIdUid(discussionID, user_id)
//		
//		IDiscussionOperator discusionOperator = session
//				.getMapper(IDiscussionOperator.class);
		
		session.commit();
		session.close();
		System.out.println("return dispo id:"+disPo.getId());
		return disPo.getId();

	}
	
	public DiscussionPo getDiscussionByName(String name) {
		SqlSession session = GlobalHolder.getSessionFactory().openSession();

		IDiscussionOperator discusionOperator = session
				.getMapper(IDiscussionOperator.class);
		
		DiscussionPo disPo = discusionOperator.getDiscussionByName(name);
		
		session.close();
		return disPo;
	}
	
	public boolean setContentOfDiscussion(DiscussionPo dis) {
		SqlSession session = GlobalHolder.getSessionFactory().openSession();

		IDiscussionOperator discusionOperator = session
				.getMapper(IDiscussionOperator.class);
		
		discusionOperator.updateContent(dis);
		discusionOperator.updateStatus(dis);
		
		session.commit();
		session.close();
		return true;
	}

	/*
	 * 返回热门的聊天主题
	 */
	public List<DiscussionPo> getHotList(int count) {
		SqlSession session = null;
		try {
			// 首先保存，聊天室主表信息
			session = GlobalHolder.getSessionFactory().openSession();

			IDiscussionOperator discusionOperator = session
					.getMapper(IDiscussionOperator.class);

			return discusionOperator.getHotDiscussion(count);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;

		} finally {
			session.close();
		}

	}

	public List<User> recommendUser(List<String> tags) {
		List<User> result = new ArrayList<User>();
		result.add(new User("1", "刘敬斌"));
		result.add(new User("2", "张凯"));
		return result;
	}

}
