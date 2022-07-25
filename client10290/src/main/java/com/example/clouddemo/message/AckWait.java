package com.example.clouddemo.message;

import com.example.clouddemo.protobuf.ChannelMessage;

import java.util.List;

public interface AckWait {
    void put(ChannelMessage.Message message);
    void remove(ChannelMessage.Message ack);
    List<ChannelMessage.Message> timeOutResend(long time);
    List<ChannelMessage.Message> giveUpResend(long time);
}
