package com.bfd.smartqa.etl.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.bfd.smartqa.etl.GlobalHolder;
import com.bfd.smartqa.etl.dao.inter.ITmpTag;
import com.bfd.smartqa.etl.dao.po.TmpTag;
import com.bfd.smartqa.etl.exception.TmpTagNotFoundException;

public class TmpTagFetcher {

	private SqlSession session;
	
	public TmpTagFetcher() {
		session = GlobalHolder.getSessionFactory().openSession();
	}
	
	public void free() {
		session.close();
	}
	
	public TmpTag getTmpTag(String Tag) throws TmpTagNotFoundException {
		ITmpTag proxy = session.getMapper(ITmpTag.class);
		TmpTag item = proxy.getTmpTag(Tag);
		if (item == null) {
			throw new TmpTagNotFoundException();
		} else {
			return item;
		}
	}
	
	public List<TmpTag> getAllTmpTag() throws TmpTagNotFoundException {
		ITmpTag proxy = session.getMapper(ITmpTag.class);
		List<TmpTag> list = proxy.getAllTmpTag();
		if (list == null) {
			throw new TmpTagNotFoundException();
		} else {
			return list;
		}
	}
	
	public void addTmpTag(TmpTag obj) {
		if (obj == null) {
			return;
		} else {
			ITmpTag proxy = session.getMapper(ITmpTag.class);
			proxy.addTmpTag(obj);
			session.commit();
		}
	}
	
	public void setTmpTag(TmpTag obj) {
		ITmpTag proxy = session.getMapper(ITmpTag.class);
		proxy.setTmpTag(obj);
		session.commit();
	}
	
	public void removeTmpTag(TmpTag obj) {
		ITmpTag proxy = session.getMapper(ITmpTag.class);
		proxy.removeTmpTag(obj);
		session.commit();
	}
}
