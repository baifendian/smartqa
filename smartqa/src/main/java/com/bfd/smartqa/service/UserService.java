package com.bfd.smartqa.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.SqlSession;

import com.bfd.smartqa.GlobalHolder;
import com.bfd.smartqa.dao.inter.IUserOperator;
import com.bfd.smartqa.dao.po.UserPo;
import com.bfd.smartqa.service.vo.User;

public class UserService {
	
	public User valid(String user_name,String password)
	{
		
		SqlSession session = GlobalHolder.getSessionFactory().openSession();

        IUserOperator userOperation=session.getMapper(IUserOperator.class);
        UserPo user = userOperation.getUserByUserName(user_name);
        session.close();
        
        if(user != null)
        {
        	if(user.getPassWord().equals(DigestUtils.md5Hex(password).toLowerCase()))
        	{
        		return new User(String.valueOf(user.getId()),user.getChineseName());
        	}
        }
        return null;
	}

}