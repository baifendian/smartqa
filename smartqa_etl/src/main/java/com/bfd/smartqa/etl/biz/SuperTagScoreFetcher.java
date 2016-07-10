package com.bfd.smartqa.etl.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.bfd.smartqa.etl.GlobalHolder;
import com.bfd.smartqa.etl.dao.inter.ISuperTagScore;
import com.bfd.smartqa.etl.dao.po.SuperTagScore;
import com.bfd.smartqa.etl.exception.SuperTagScoreNotFoundException;

public class SuperTagScoreFetcher {

	private SqlSession session;
	
	public SuperTagScoreFetcher() {
		session = GlobalHolder.getSessionFactory().openSession();
	}
	
	public void free() {
		session.close();
	}
	
	public List<SuperTagScore> getAllSuperTagScore() {
		ISuperTagScore proxy = session.getMapper(ISuperTagScore.class);
		List<SuperTagScore> list = proxy.getAllSuperTagScore();
		return list;
	}
	
	public SuperTagScore getSuperTagScore(int Uid, int SuperId) throws SuperTagScoreNotFoundException {
		ISuperTagScore proxy = session.getMapper(ISuperTagScore.class);
		SuperTagScore item = proxy.getSuperTagScore(Uid, SuperId);
		if (item == null) {
			throw new SuperTagScoreNotFoundException();
		}
		return item;
	}
	
	public void setUserTagScore(SuperTagScore obj) {
		ISuperTagScore proxy = session.getMapper(ISuperTagScore.class);
		proxy.updateSuperTagScore(obj);
		session.commit();
	}
	
	public void addUserTagScore(SuperTagScore obj) {
		if (obj == null) {
			return;
		} else {
			ISuperTagScore proxy = session.getMapper(ISuperTagScore.class);
			proxy.addUserTagScore(obj);
			session.commit();
		}
	}
}
