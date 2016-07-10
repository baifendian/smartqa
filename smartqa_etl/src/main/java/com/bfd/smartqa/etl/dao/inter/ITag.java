package com.bfd.smartqa.etl.dao.inter;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.bfd.smartqa.etl.dao.po.Tag;

public interface ITag {

	@Select("select * from smart_ods_tag")
	public List<Tag> getAllTag();
	
	@Select("select * from smart_ods_tag where Tag = #{Tag}")
	public Tag getTag(String Tag);
	
	@Insert("insert into smart_ods_tag(Tag) values (#{Tag})")
	public void addTag(Tag obj);
}
