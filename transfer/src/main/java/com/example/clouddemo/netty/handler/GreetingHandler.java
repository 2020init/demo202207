package com.example.clouddemo.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.clouddemo.cache.CopyOnWriteMap;
import com.example.clouddemo.constant.AttributeKeyConstant;
import com.example.clouddemo.constant.MessageKeyConstants;
import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.controller.TransferBootstrap;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
public class GreetingHandler extends SimpleChannelInboundHandler<Message> {
    private TransferBootstrap bootstrap;
    private CopyOnWriteMap ids2connectorsMap;


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message msg) throws Exception {
        log.info("**************" + msg.toString());
        if(!msg.getClass().equals(ChannelMessage.Message.class)){
            return;
        }
        ChannelMessage.Message message = (ChannelMessage.Message) msg;
        if(message.getType() != MessageTypeConstants.GREETING){
            channelHandlerContext.fireChannelRead(message);
            return;
        }
        String id = message.getFromUid();
        channelHandlerContext.channel().attr(AttributeKeyConstant.CHANNEL_ID).set(id);
        ids2connectorsMap.put(id, channelHandlerContext, 1);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelMessage.Message message = ChannelMessage.Message.newBuilder()
                .setType(MessageTypeConstants.GREETING)
                .setFromUid(TransferBootstrap.UUID)
                .setTimestamp(System.currentTimeMillis())
                .build();
        ctx.writeAndFlush(message);
    }
}
