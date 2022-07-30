package com.example.clouddemo.redis;

import com.example.clouddemo.service.TokenService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TokenServiceTestImpl implements TokenService {
    @Override
    public boolean checkTokenAndUid(String uid, String token) {
        return true;
    }

    @Override
    public void removeTokenAndUid(String uid, String token) {

    }

    @Override
    public void putTokenAndUid(String uid, String token) {

    }
}
