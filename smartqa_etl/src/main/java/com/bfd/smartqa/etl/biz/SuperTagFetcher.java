package com.bfd.smartqa.etl.biz;

import org.apache.ibatis.session.SqlSession;

import com.bfd.smartqa.etl.GlobalHolder;
import com.bfd.smartqa.etl.dao.inter.ISuperTag;
import com.bfd.smartqa.etl.dao.po.SuperTag;

public class SuperTagFetcher {

	private SqlSession session;
	
	public SuperTagFetcher() {
		session = GlobalHolder.getSessionFactory().openSession();
	}
	
	public void free() {
		session.close();
	}
	
	public SuperTag getSuperTag(int tagID) {
		ISuperTag proxy = session.getMapper(ISuperTag.class);
		SuperTag item = proxy.getSuperTag(tagID);
		return item;
	}
	
	public int getMaxSuperID() {
		ISuperTag proxy = session.getMapper(ISuperTag.class);
		return proxy.getMaxSuperID();
	}
	
	public void addSuperTag(SuperTag obj) {
		if (obj == null) {
			return;
		} else {
			ISuperTag proxy = session.getMapper(ISuperTag.class);
			proxy.addSuperTag(obj);
			session.commit();
		}
	}
}
