package com.bfd.smartqa.etl.dao.inter;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bfd.smartqa.etl.dao.po.TmpTag;

public interface ITmpTag {

	@Select("select * from smart_ods_tmp_tag where Tag = #{Tag}")
	public TmpTag getTmpTag(String Tag);
	
	@Select("select * from smart_ods_tmp_tag where Status = 0")
	public List<TmpTag> getAllTmpTag();
	
	@Insert("insert into smart_ods_tmp_tag(Tag,Hotpoint) values (#{Tag}, #{HotPoint})")
	public void addTmpTag(TmpTag obj);
	
	@Update("update smart_ods_tmp_tag set HotPoint = #{HotPoint} where Tag = #{Tag}")
	public void setTmpTag(TmpTag obj);
	
	@Update("update smart_ods_tmp_tag set Status = 1 where Tag = #{Tag}")
	public void removeTmpTag(TmpTag obj);
}
