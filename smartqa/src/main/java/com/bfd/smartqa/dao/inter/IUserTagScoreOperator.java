package com.bfd.smartqa.dao.inter;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.bfd.smartqa.dao.po.UserTagScorePo;


public interface IUserTagScoreOperator {

	@Select("select * from smart_mid_user_tag_score where TagID = #{tagId}")
	public List<UserTagScorePo> getUserTagScoreByTagID(int tagId);
}
