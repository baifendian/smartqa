package com.bfd.smartqa.etl;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.bfd.smartqa.etl.dao.inter.IDiscussion;
import com.bfd.smartqa.etl.dao.inter.ISuperTag;
import com.bfd.smartqa.etl.dao.inter.ISuperTagScore;
import com.bfd.smartqa.etl.dao.inter.ITag;
import com.bfd.smartqa.etl.dao.inter.ITmpTag;
import com.bfd.smartqa.etl.dao.inter.IUser;
import com.bfd.smartqa.etl.dao.inter.IUserTagScore;

public class GlobalHolder {
	
	private static SqlSessionFactory sqlSessionFactory;
	
	public static void init()
	{
		try{
        	//mybatis的配置文件
            String resource = "configuration.xml";
            //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
            InputStream is = GlobalHolder.class.getClassLoader().getResourceAsStream(resource);
//            System.out.println("is is null:"+ (is == null));
            //构建sqlSession的工厂
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            sqlSessionFactory.getConfiguration().addMapper(IUser.class);
            sqlSessionFactory.getConfiguration().addMapper(ISuperTag.class);
            sqlSessionFactory.getConfiguration().addMapper(IUserTagScore.class);
            sqlSessionFactory.getConfiguration().addMapper(IDiscussion.class);
            sqlSessionFactory.getConfiguration().addMapper(ISuperTagScore.class);
            sqlSessionFactory.getConfiguration().addMapper(ITag.class);
            sqlSessionFactory.getConfiguration().addMapper(ITmpTag.class);
        }catch(Exception e){
            e.printStackTrace();
        }
	}

    public static SqlSessionFactory getSessionFactory(){
        return sqlSessionFactory;
    }

}
