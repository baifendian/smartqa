package com.bfd.smartqa.etl.dao.inter;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bfd.smartqa.etl.dao.po.SuperTagScore;

public interface ISuperTagScore {

	@Select("select * from smart_dw_user_supertag_score")
	public List<SuperTagScore> getAllSuperTagScore();
	
	@Select("select * from smart_dw_user_supertag_score where Uid = #{param1} and SuperID = #{param2}")
	public SuperTagScore getSuperTagScore(int Uid, int SuperID);
	
	@Update("update smart_dw_user_supertag_score set Score = #{Score} where Uid = #{Uid} and SuperID = #{SuperID}")
	public void updateSuperTagScore(SuperTagScore obj);
	
	@Insert("insert into smart_dw_user_supertag_score(Uid,SuperID,Score) values(#{Uid}, #{SuperID}, #{Score})")
	public void addUserTagScore(SuperTagScore obj);
}
