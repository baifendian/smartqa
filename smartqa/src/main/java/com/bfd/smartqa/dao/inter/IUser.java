package com.bfd.smartqa.dao.inter;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.bfd.smartqa.dao.po.User;

public interface IUser {
	
	@Select("select * from users where id= #{id}")
	public User getUser(int id);
	
	@Select("select * from users")
	public List<User> getAllUser();
}
