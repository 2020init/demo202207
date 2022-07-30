package com.example.clouddemo.handler;

import com.example.clouddemo.config.ClientBootstrap;
import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class AuthenticHandler extends SimpleChannelInboundHandler<Message> {
    private ChannelHandlerContext context;
    private String uid = ClientBootstrap.getUuid();
    private boolean isAddGroup = false;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        if(msg.getClass() != ChannelMessage.Message.class){
            return;
        }
        ChannelMessage.Message message = (ChannelMessage.Message) msg;
        if(message.getType() != MessageTypeConstants.GREETING){
            ctx.fireChannelRead(msg);
            return;
        }

        System.out.println("receive GREETING message from server " + message.getFromUid());

        if(!isAddGroup){
            ChannelMessage.Message addGroupMsg = ChannelMessage.Message.newBuilder()
                    .setFromUid(uid)
                    .setToUid("1")
                    .setType(MessageTypeConstants.ADD_GROUP)
                    .build();
            ctx.writeAndFlush(addGroupMsg);
            isAddGroup = true;
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
        ChannelMessage.Message message = ChannelMessage.Message.newBuilder()
                .setFromUid(uid)
                .setBody(ClientBootstrap.test_token)//token
                .setTimestamp(System.currentTimeMillis())
                .setType(MessageTypeConstants.AUTH)
                .build();
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        context = null;
    }

    public ChannelHandlerContext getContext(){
        return context;
    }
    public String getUid(){
        return uid;
    }
}
