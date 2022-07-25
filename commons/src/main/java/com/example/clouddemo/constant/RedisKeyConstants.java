package com.example.clouddemo.constant;

public class RedisKeyConstants {
    public static final String ONLINE = "ONLINE";
    public static final String RELATIONSHIP = "RELATIONSHIP";
    public static final String TOKEN2UID = "TOKEN2UID";
    public static final String UID2TOKEN = "UID2TOKEN";
    public static final String OFFLINE_MESSAGE_LIST = "OFFLINE_MESSAGE_LIST";
    public static final String OFFLINE_MESSAGE_SEQ = "OFFLINE_MESSAGE_SEQ";
    public static final String GROUP_ONLINE_MEMBER_SET = "GROUP_ONLINE_MEMBER_SET";
    public static final String GROUP_VERSION = "GROUP_VERSION";
    public static final String GROUP_EVENT_LIST = "GROUP_EVENT_LIST";

    public static String TokenIdKeyGenerate(String token, String id){
        return token + ":" + id;
    }
    public static String OfflineMessageListKeyGenerate(String toUid){
        return OFFLINE_MESSAGE_LIST + ":" + toUid;
    }
    public static String OfflineMessageGlobalSeqKeyGenerate(long globalSeq){
        return OFFLINE_MESSAGE_SEQ + ":" + globalSeq;
    }

    public static String GroupOnlineMemberKeyGenerate(long group_id){
        return GROUP_ONLINE_MEMBER_SET + ":" + group_id;
    }

    public static String GroupVersionKeyGenerate(long group_id){
        return GROUP_VERSION + ":" + group_id;
    }

    public static String GroupEventListKeyGenerate(long group_id){
        return GROUP_EVENT_LIST + ":" + group_id;
    }


}
