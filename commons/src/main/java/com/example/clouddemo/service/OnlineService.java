package com.example.clouddemo.service;

public interface OnlineService {
    boolean isOnline(String uid);
    String getConnectorId(String uid);
    void online(String uid, String connectorId);
    void offline(String uid);
    void joinGroup(String uid, long group_id);
    void leaveGroup(String uid, long group_id);
}
