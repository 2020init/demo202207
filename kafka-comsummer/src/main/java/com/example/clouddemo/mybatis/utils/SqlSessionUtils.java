package com.example.clouddemo.mybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionUtils {
    private static SqlSessionFactory sqlSessionFactory = null;

    public static SqlSession getSqlSession(){
        if(sqlSessionFactory == null){
            synchronized (SqlSessionUtils.class){
                if(sqlSessionFactory == null){
                    try {
                        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
                        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sqlSessionFactory.openSession(true);
    }
}
