package com.bfd.smartqa;

import java.io.File;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import com.bfd.smartqa.analysis.Segmenter;
import com.bfd.smartqa.dao.inter.IDiscussionOperator;
import com.bfd.smartqa.dao.inter.ISuperTagOperator;
import com.bfd.smartqa.dao.inter.ISuperTagScoreOperator;
import com.bfd.smartqa.dao.inter.ITagOperator;
import com.bfd.smartqa.dao.inter.IUserOperator;
import com.bfd.smartqa.dao.inter.IUserTagScoreOperator;

public class GlobalHolder {
	
	private static SqlSessionFactory sqlSessionFactory;
	private static Logger logger = Logger.getLogger(GlobalHolder.class);
	private static Segmenter segmenter = null;
	
	public static void init()
	{
		try{
        	//mybatis的配置文件
            String resource = "configuration.xml";
            //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
            InputStream is = GlobalHolder.class.getClassLoader().getResourceAsStream(resource);
            //构建sqlSession的工厂
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            sqlSessionFactory.getConfiguration().addMapper(IUserOperator.class);
            sqlSessionFactory.getConfiguration().addMapper(IDiscussionOperator.class);
            sqlSessionFactory.getConfiguration().addMapper(ITagOperator.class);
            sqlSessionFactory.getConfiguration().addMapper(ISuperTagOperator.class);
            sqlSessionFactory.getConfiguration().addMapper(ISuperTagScoreOperator.class);
            sqlSessionFactory.getConfiguration().addMapper(IUserTagScoreOperator.class);
        }catch(Exception e){
            e.printStackTrace();
        }
		
		//TODO
		//System.out.println("====="+GlobalHolder.class.getResource("").getPath().toString());
		File file = new File(System.getProperty("user.dir")+"/src/main/resources");
		//System.out.println("====="+System.getProperty("user.dir"));
		File stopword = new File(System.getProperty("user.dir")+"/src/main/resources/word.stop");
		segmenter = new Segmenter(file.toPath(),stopword.toPath());
	}

    public static SqlSessionFactory getSessionFactory(){
        return sqlSessionFactory;
    }
    
    public static Logger getLogger()
    {
    	return logger;
    }
    
    public static Segmenter getSegmenter()
    {
    	return segmenter;
    }

}
