package com.bfd.smartqa.dao.inter;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.bfd.smartqa.dao.po.SuperTagPo;


public interface ISuperTagOperator {

	@Select("select * from smart_dw_supertag")
	public List<SuperTagPo> getAllSuperTagPo();
	
	@Select("select * from smart_dw_supertag where TagID = #{tagID}")
	public SuperTagPo getSuperTagPo(int tagID);
	
	@Select("select max(SuperID) from smart_dw_supertag")
	public int getMaxSuperID();
	
	@Insert("insert into smart_dw_supertag(SuperID, TagID) values (#{SuperID}, #{TagID})")
	public void addSuperTagPo(SuperTagPo obj);
}
