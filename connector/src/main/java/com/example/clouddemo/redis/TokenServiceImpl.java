package com.example.clouddemo.redis;

import com.example.clouddemo.constant.RedisKeyConstants;
import com.example.clouddemo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class TokenServiceImpl implements TokenService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean checkTokenAndUid(String uid, String token) {
        String key = RedisKeyConstants.TokenIdKeyGenerate(token, uid);
        boolean ans = redisTemplate.opsForValue().get(key) != null;
        if(ans){
            redisTemplate.expire(key, 240, TimeUnit.HOURS);
        }
        return ans;
    }

    @Override
    public void removeTokenAndUid(String uid, String token) {
        String key = RedisKeyConstants.TokenIdKeyGenerate(token, uid);
        redisTemplate.delete(key);
    }

    @Override
    public void putTokenAndUid(String uid, String token) {
        String key = RedisKeyConstants.TokenIdKeyGenerate(token, uid);
        redisTemplate.opsForValue().set(key, "1");
        redisTemplate.expire(key, 2, TimeUnit.HOURS);
    }
}
