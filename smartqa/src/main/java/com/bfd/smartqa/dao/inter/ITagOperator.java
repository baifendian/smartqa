package com.bfd.smartqa.dao.inter;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.bfd.smartqa.dao.po.TagPo;


public interface ITagOperator {

	@Select("select * from smart_ods_tag")
	public List<TagPo> getAllTag();
	
	@Select("select * from smart_ods_tag where Tag = #{Tag}")
	public TagPo getTag(String Tag);
	
	@Insert("insert into smart_ods_tag(Tag) values (#{Tag})")
	public void addTag(TagPo obj);
}
