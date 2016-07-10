package com.bfd.smartqa.dao.inter;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.bfd.smartqa.dao.po.UserPo;

public interface IUserOperator {
	
	@Select("select * from smart_ods_user where UserName= #{userName}")
	public UserPo getUserByUserName(String userName);
	
	@Select("select * from smart_ods_user where id= #{id}")
	public UserPo getUser(int id);
	
	@Select("select * from smart_ods_user")
	public List<UserPo> getAllUser();
}
