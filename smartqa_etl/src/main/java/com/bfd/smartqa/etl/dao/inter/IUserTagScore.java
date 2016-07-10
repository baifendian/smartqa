package com.bfd.smartqa.etl.dao.inter;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bfd.smartqa.etl.dao.po.UserTagScore;

public interface IUserTagScore {

	@Select("select * from smart_mid_user_tag_score")
	public List<UserTagScore> getAllUserTagScore();
	
	@Select("select * from smart_mid_user_tag_score where TagID = #{param1} and Uid = #{param2}")
	public UserTagScore getUserTagScore(int tagId, int uId);
	
	@Update("update smart_mid_user_tag_score set Score = #{Score} where Uid = #{Uid} and TagID = #{TagID}")
	public void updateUserTagScore(UserTagScore obj);
	
	@Insert("insert into smart_mid_user_tag_score(Uid,TagID,Score) values(#{Uid}, #{TagID}, #{Score})")
	public void addUserTagScore(UserTagScore obj);
}
