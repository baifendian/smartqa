package com.bfd.smartqa.etl.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.bfd.smartqa.etl.GlobalHolder;
import com.bfd.smartqa.etl.dao.inter.IDiscussion;
import com.bfd.smartqa.etl.dao.po.Discussion;

public class DiscussionFetcher {

	private SqlSession session;
	
	public DiscussionFetcher() {
		session = GlobalHolder.getSessionFactory().openSession();
	}
	
	public void free() {
		session.close();
	}
	
	public List<Discussion> getAllDiscussion() {
		IDiscussion proxy = session.getMapper(IDiscussion.class);
		List<Discussion> list = proxy.getAllDiscussion();
		return list;
	}
	
	public List<Discussion> getFullDiscussion() {
		IDiscussion proxy = session.getMapper(IDiscussion.class);
		List<Discussion> list = proxy.getFullDiscussion();
		return list;
	}
	
	public void setDiscussion(Discussion obj) {
		IDiscussion proxy = session.getMapper(IDiscussion.class);
		proxy.updateDiscussion(obj);
		session.commit();
	}
	
	public void setHot(Discussion obj) {
		IDiscussion proxy = session.getMapper(IDiscussion.class);
		proxy.updateHot(obj);
		session.commit();
	}
}
