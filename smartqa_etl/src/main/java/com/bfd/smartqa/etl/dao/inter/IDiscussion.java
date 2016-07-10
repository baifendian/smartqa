package com.bfd.smartqa.etl.dao.inter;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bfd.smartqa.etl.dao.po.Discussion;

public interface IDiscussion {

	@Select("select * from smart_ods_discussion where Ishandle = 0 and Status = 1")
	public List<Discussion> getAllDiscussion();
	
	@Select("select * from smart_ods_discussion where Status = 1")
	public List<Discussion> getFullDiscussion();
	
	@Update("update smart_ods_discussion set Ishandle = 1 where ID = #{ID}")
	public void updateDiscussion(Discussion obj);
	
	@Update("update smart_ods_discussion set Hot = #{Hot} where ID = #{ID}")
	public void updateHot(Discussion obj);
}
