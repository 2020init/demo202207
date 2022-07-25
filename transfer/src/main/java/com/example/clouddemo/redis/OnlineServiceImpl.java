package com.example.clouddemo.redis;

import com.example.clouddemo.constant.RedisKeyConstants;
import com.example.clouddemo.service.OnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class OnlineServiceImpl implements OnlineService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String getConnectorId(String uid) {
        return (String) redisTemplate.opsForHash().get(RedisKeyConstants.ONLINE, uid);
    }

    @Override
    public boolean isOnline(String uid) {
        return redisTemplate.opsForHash().hasKey(RedisKeyConstants.ONLINE, uid);
    }

    @Override
    public void online(String uid, String connectorId) {
        redisTemplate.opsForHash().put(RedisKeyConstants.ONLINE, uid, connectorId);
    }

    @Override
    public void offline(String uid) {
        redisTemplate.opsForHash().delete(uid);
    }

    @Override
    public void joinGroup(String uid, long group_id) {

    }

    @Override
    public void leaveGroup(String uid, long group_id) {

    }
}
