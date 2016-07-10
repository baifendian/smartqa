package com.bfd.smartqa.etl.tool;

import java.util.List;

import com.bfd.smartqa.etl.GlobalHolder;
import com.bfd.smartqa.etl.biz.SuperTagFetcher;
import com.bfd.smartqa.etl.biz.SuperTagScoreFetcher;
import com.bfd.smartqa.etl.biz.UserTagScoreFetcher;
import com.bfd.smartqa.etl.dao.po.SuperTag;
import com.bfd.smartqa.etl.dao.po.SuperTagScore;
import com.bfd.smartqa.etl.dao.po.UserTagScore;
import com.bfd.smartqa.etl.exception.SuperTagScoreNotFoundException;

public class InitSuperTagScore {

	public static void main(String[] args) {
		GlobalHolder.init();
		
		UserTagScoreFetcher uts = new UserTagScoreFetcher();
		SuperTagFetcher st = new SuperTagFetcher();
		SuperTagScoreFetcher sts = new SuperTagScoreFetcher();
		
		List<UserTagScore> list = uts.getAllUserTagScore();
		
		for (UserTagScore item : list) {
			int uid = item.getUid();
			int tagid = item.getTagID();
			SuperTag supertag = st.getSuperTag(tagid);
			if (supertag == null) {
				continue;
			}
			int superid = supertag.getSuperID();
			try {
				sts.getSuperTagScore(uid, superid);
			} catch (SuperTagScoreNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				SuperTagScore i = new SuperTagScore();
				i.setScore(20);
				i.setSuperID(superid);
				i.setUid(uid);
				sts.addUserTagScore(i);
			}
		}
		
		uts.free();
		st.free();
		sts.free();
	}
}
