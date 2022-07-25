package com.example.clouddemo.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

public interface MessageMapper {
    void insertMessage(@Param("from_uid") String fromUid,
                       @Param("to_uid") String toUid,
                       @Param("body") String body,
                       @Param("time_stamp") long timeStamp,
                       @Param("global_seq") String globalSeq,
                       @Param("session_seq") String sessionSeq);
}
