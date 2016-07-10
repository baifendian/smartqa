package com.bfd.smartqa.etl.dao.inter;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.bfd.smartqa.etl.dao.po.SuperTag;

public interface ISuperTag {

	@Select("select * from smart_dw_supertag")
	public List<SuperTag> getAllSuperTag();
	
	@Select("select * from smart_dw_supertag where TagID = #{tagID}")
	public SuperTag getSuperTag(int tagID);
	
	@Select("select max(SuperID) from smart_dw_supertag")
	public int getMaxSuperID();
	
	@Insert("insert into smart_dw_supertag(SuperID, TagID) values (#{SuperID}, #{TagID})")
	public void addSuperTag(SuperTag obj);
}
