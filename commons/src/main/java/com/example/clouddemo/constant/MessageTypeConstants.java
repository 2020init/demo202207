package com.example.clouddemo.constant;

import com.example.clouddemo.protobuf.ChannelMessage;

public class MessageTypeConstants {
    public static final int GREETING = 100;
    //authentic
    public static final int AUTH = 101;

    //R package
    public static final int REQUEST = 200;
    //A package
    public static final int CONNECTOR_ACK = 300;
    public static final int ACK_FROM_CLIENT = 301;
    public static final int CANNOT_REACH = 302;

    //N package
    public static final int NOTIFY = 400;
    public static final int ACK_FROM_SERVER = 401;

    //Group
    public static final int ADD_GROUP = 1001;
    public static final int GROUP_MESSAGE = 1002;

    public static final int MAKE_A_FRIEND = 500;
    public static final int GOODBYE = 800;
    public static final int HEARTBEAT = 999;


    public static ChannelMessage.Message setMessageType(ChannelMessage.Message msg, int type){
        return ChannelMessage.Message.newBuilder()
                    .setTimestamp(msg.getTimestamp())
                    .setType(type)
                    .setBody(msg.getBody())
                    .setFromUid(msg.getFromUid())
                    .setToUid(msg.getToUid())
                    .setGlobalSequence(msg.getGlobalSequence())
                    .setSessionSequence(msg.getSessionSequence())
                    .build();

    }
}
