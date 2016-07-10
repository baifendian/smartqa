package com.bfd.smartqa.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.bfd.smartqa.GlobalHolder;
import com.bfd.smartqa.dao.inter.IDiscussionOperator;
import com.bfd.smartqa.dao.inter.ISuperTagOperator;
import com.bfd.smartqa.dao.inter.ISuperTagScoreOperator;
import com.bfd.smartqa.dao.inter.ITagOperator;
import com.bfd.smartqa.dao.inter.IUserOperator;
import com.bfd.smartqa.dao.inter.IUserTagScoreOperator;
import com.bfd.smartqa.dao.po.DiscussionPo;
import com.bfd.smartqa.dao.po.SuperTagPo;
import com.bfd.smartqa.dao.po.SuperTagScorePo;
import com.bfd.smartqa.dao.po.TagPo;
import com.bfd.smartqa.dao.po.UserPo;
import com.bfd.smartqa.dao.po.UserTagScorePo;
import com.bfd.smartqa.service.vo.Discussion;
import com.bfd.smartqa.service.vo.User;

public class RecommendService {
	
	/*
	 * 根据标签的列表，返回相关的用户信息
	 */
	@SuppressWarnings("unchecked")
	public List<User> recommendUser(List<String> tags)
	{
		List<User> result = new ArrayList<User>();
		
		Map<Integer,Integer> uidScore = this.searchUserByTagBiz(tags);
		SqlSession session = GlobalHolder.getSessionFactory().openSession();
		IUserOperator userOp = session.getMapper(IUserOperator.class);
		
		for (int uid : uidScore.keySet()) {
			System.out.println("====uid:" + uid);
			System.out.println("====score:" + uidScore.get(uid));
			UserPo userPo = userOp.getUser(uid);
			String userName = userPo.getChineseName();
			User user = new User(String.valueOf(uid), userName);
			user.setScore(uidScore.get(uid));
			result.add(user);
		}
		
		session.close();
		
		Collections.sort(result, new ComparatorUser());
		
		return result;
	}
	
	/*
	 * 根据标签的列表，返回相关的讨论话题
	 */
	public List<Discussion> recommendDiscussion(List<String> tags)
	{
		List<Discussion> result = new ArrayList<Discussion>();
		
		List<DiscussionPo> list = this.searchDiscussionByTag(tags);
		
		for (DiscussionPo item : list) {
			Discussion dis = new Discussion();
			dis.setId(String.valueOf(item.getId()));
			dis.setSubject(item.getCaption());
			result.add(dis);
		}
		
		return result;
	}
	
	
	private List<DiscussionPo> searchDiscussionByTag(List<String> tags) {
		List<DiscussionPo> result = new ArrayList<DiscussionPo>();
		
		SqlSession session = GlobalHolder.getSessionFactory().openSession();
		IDiscussionOperator discussionOp = session.getMapper(IDiscussionOperator.class);
		
		List<DiscussionPo> list = discussionOp.getAllDiscussion();
		
		for (DiscussionPo item : list) {
			for (String tag : tags) {
				boolean flag = this.searchCaptionTag(item.getCaptionintag(), tag);
				if (flag) {
					result.add(item);
					break;
				}
			}
		}
		
		return result;
	}
	
	private boolean searchCaptionTag(String captionInTag, String tagName) {
		String[] tags =  captionInTag.split(",");
		boolean result = false;
		
		for (int i=0;i<tags.length;i++) {
			if (tags[i].equals(tagName)) {
				result = true;
			}
		}
		return result;
	}
	
	
	private class ComparatorUser implements Comparator {

		@Override
		public int compare(Object o1, Object o2) {
			// TODO Auto-generated method stub
			User user1 = (User) o1;
			User user2 = (User) o2;
			
			if (user1.getScore() == user2.getScore()) {
				return 0;
			} else if (user1.getScore() > user2.getScore()) {
				return -1;
			} else {
				return 1;
			}
		}
		
	}
	
	/**
	 * 根据标签列表，查询符合经验值的用户
	 */
	private Map<Integer,Integer> searchUserByTagBiz(List<String> tags) {
		Map<Integer,Integer> result = new HashMap<Integer,Integer>();
		
		SqlSession session = GlobalHolder.getSessionFactory().openSession();
		ITagOperator tagOp = session.getMapper(ITagOperator.class);
		ISuperTagOperator superTagOp = session.getMapper(ISuperTagOperator.class);
		ISuperTagScoreOperator superTagScoreOp = session.getMapper(ISuperTagScoreOperator.class);
		IUserTagScoreOperator userTagScoreOp = session.getMapper(IUserTagScoreOperator.class);
		
		Set<Integer> uidSet = new HashSet<Integer>();
		Set<Integer> superIdSet = new HashSet<Integer>();
		
		for (String tag : tags) {
			TagPo tagPo = tagOp.getTag(tag);
			int tagId;
			if (tagPo != null) {
				tagId = tagPo.getID();
//				List<UserTagScorePo> list = userTagScoreOp.getUserTagScoreByTagID(tagId);
//				for (UserTagScorePo item : list) {
//					uidSet.add(item.getUid());
//				}
			} else {
				continue;
			}
			
			SuperTagPo superTagPo = superTagOp.getSuperTagPo(tagId);
			if (superTagPo != null) {
				superIdSet.add(superTagPo.getSuperID());
			}
		}
		
//		for (int uid : uidSet) {
//			float sumScore = 0F;
			
		for (int superId : superIdSet) {
			List<SuperTagScorePo> superTagScorePoList = superTagScoreOp
					.getSuperTagScorePoBySuperID(superId);
			for (SuperTagScorePo superTagScorePo : superTagScorePoList) {
				if (superTagScorePo != null) {
					float score = superTagScorePo.getScore();
					int uid = superTagScorePo.getUid();

					if (result.containsKey(uid)) {
						int finalScore = result.get(uid) + (int) score;
						result.put(uid, finalScore);
					} else {
						result.put(uid, (int) score);
					}
				}
			}
		}
			
//			result.put(uid, (int)sumScore);
//		}
		
		session.close();
		
		return result;
	}
	
	public static void main(String[] args) {
		GlobalHolder.init();
		RecommendService demo = new RecommendService();
		List<String> list = new ArrayList<String>();
		list.add("百分点大数据操作系统");
		list.add("百分点操作系统");
		list.add("大数据操作系统");
		
		List<Discussion> result = demo.recommendDiscussion(list);
		
		for (Discussion item : result) {
			System.out.println(item.getSubject());
		}
	}

}
