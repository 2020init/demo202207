package com.example.clouddemo.mybatis.service;

import org.apache.ibatis.annotations.Param;

public interface MessageService {
    void insert(String fromUid,
                String toUid,
                String body,
                long timeStamp,
                String globalSeq,
                String sessionSeq);
}
