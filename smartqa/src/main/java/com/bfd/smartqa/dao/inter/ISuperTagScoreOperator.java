package com.bfd.smartqa.dao.inter;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bfd.smartqa.dao.po.SuperTagScorePo;


public interface ISuperTagScoreOperator {

	@Select("select * from smart_dw_user_supertag_score")
	public List<SuperTagScorePo> getAllSuperTagScorePo();
	
	@Select("select * from smart_dw_user_supertag_score where Uid = #{param1} and SuperID = #{param2}")
	public SuperTagScorePo getSuperTagScorePo(int Uid, int SuperID);
	
	@Select("select * from smart_dw_user_supertag_score where SuperID = #{SuperID}")
	public List<SuperTagScorePo> getSuperTagScorePoBySuperID(int SuperID);
	
	@Update("update smart_dw_user_supertag_score set Score = #{Score} where Uid = #{Uid} and SuperID = #{SuperID}")
	public void updateSuperTagScorePo(SuperTagScorePo obj);
	
	@Insert("insert into smart_dw_user_supertag_score(Uid,SuperID,Score) values(#{Uid}, #{SuperID}, #{Score})")
	public void addUserTagScore(SuperTagScorePo obj);
}
