package com.example.clouddemo.constant;

import io.netty.util.AttributeKey;

public class AttributeKeyConstant {
    public static final AttributeKey<String> CHANNEL_ID = AttributeKey.valueOf("CID");
    public static final AttributeKey<String> CHANNEL_USER_TOKEN = AttributeKey.valueOf("TOKEN");
}
