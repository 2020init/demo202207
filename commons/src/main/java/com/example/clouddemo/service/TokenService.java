package com.example.clouddemo.service;

public interface TokenService {
    boolean checkTokenAndUid(String uid, String token);
    void removeTokenAndUid(String uid, String token);
    void putTokenAndUid(String uid, String token);
}
