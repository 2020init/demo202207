package com.example.clouddemo.mybatis.service;

import com.example.clouddemo.mybatis.mapper.MessageMapper;
import com.example.clouddemo.mybatis.utils.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;

public class MessageServiceImpl implements MessageService{
    private SqlSession sqlSession;
    private MessageMapper mapper;


    public MessageServiceImpl(){


    }

    @Override
    public void insert(String fromUid, String toUid, String body, long timeStamp, String globalSeq, String sessionSeq) {
        sqlSession = SqlSessionUtils.getSqlSession();
        try {
            mapper = sqlSession.getMapper(MessageMapper.class);
            mapper.insertMessage(fromUid,
                    toUid,
                    body,
                    timeStamp,
                    globalSeq,
                    sessionSeq);
        }finally {
            //主动将连接归还, 不要等到sqlSessionFactory主动去询问
            sqlSession.close();
        }


    }
}
