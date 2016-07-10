package com.bfd.smartqa.etl.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.bfd.smartqa.etl.GlobalHolder;
import com.bfd.smartqa.etl.dao.inter.IUserTagScore;
import com.bfd.smartqa.etl.dao.po.UserTagScore;
import com.bfd.smartqa.etl.exception.UserTagScoreNotFoundException;

public class UserTagScoreFetcher {

	private SqlSession session;
	
	public UserTagScoreFetcher() {
		session = GlobalHolder.getSessionFactory().openSession();
	}
	
	public void free() {
		session.close();
	}
	
	public List<UserTagScore> getAllUserTagScore() {
		IUserTagScore proxy = session.getMapper(IUserTagScore.class);
		List<UserTagScore> list = proxy.getAllUserTagScore();
		return list;
	}
	
	public UserTagScore getUserTagScore(int tagId, int uId) throws UserTagScoreNotFoundException {
		IUserTagScore proxy = session.getMapper(IUserTagScore.class);
		UserTagScore item = proxy.getUserTagScore(tagId, uId);
		if (item == null) {
			throw new UserTagScoreNotFoundException();
		}
		
		return item;
	}
	
	public void setUserTagScore(UserTagScore obj) {
		IUserTagScore proxy = session.getMapper(IUserTagScore.class);
		proxy.updateUserTagScore(obj);
		session.commit();
	}
	
	public void addUserTagScore(UserTagScore obj) {
		if (obj == null) {
			return;
		} else {
			IUserTagScore proxy = session.getMapper(IUserTagScore.class);
			proxy.addUserTagScore(obj);
			session.commit();
		}
	}
	
	public static void main(String[] args) {
		GlobalHolder.init();
		UserTagScoreFetcher obj = new UserTagScoreFetcher();
		UserTagScore aa = new UserTagScore();
		aa.setScore(22.5F);
		aa.setTagID(5);
		aa.setUid(13);
		obj.addUserTagScore(aa);
		obj.free();
	}
}
