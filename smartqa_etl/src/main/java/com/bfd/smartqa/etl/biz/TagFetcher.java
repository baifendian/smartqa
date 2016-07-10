package com.bfd.smartqa.etl.biz;

import org.apache.ibatis.session.SqlSession;

import com.bfd.smartqa.etl.GlobalHolder;
import com.bfd.smartqa.etl.dao.inter.ITag;
import com.bfd.smartqa.etl.dao.po.Tag;
import com.bfd.smartqa.etl.exception.TagNotFoundException;

public class TagFetcher {

	private SqlSession session;
	
	public TagFetcher() {
		session = GlobalHolder.getSessionFactory().openSession();
	}
	
	public void free() {
		session.close();
	}
	
	public Tag getTagByName(String name) throws TagNotFoundException {
		ITag proxy = session.getMapper(ITag.class);
		Tag item = proxy.getTag(name);
		if (item == null) {
			throw new TagNotFoundException();
		}
		return item;
	}
	
	public void addTag(Tag tag) {
		if (tag == null) {
			return;
		} else {
			ITag proxy = session.getMapper(ITag.class);
			proxy.addTag(tag);
			session.commit();
		}
	}
	
	public static void main(String[] args) {
//		GlobalHolder.init();
//		TagFetcher fetcher = new TagFetcher();
//		Tag demo = fetcher.getTagByName("notfound");
//		System.out.println(demo);
	}
}
