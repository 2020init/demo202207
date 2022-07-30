package com.example.clouddemo.handler;

import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@ChannelHandler.Sharable
public class NotifyMessageHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        ChannelMessage.Message message = (ChannelMessage.Message) msg;
        if(message.getType() != MessageTypeConstants.NOTIFY && message.getType() != MessageTypeConstants.GROUP_MESSAGE){
            ctx.writeAndFlush(msg);
            return;
        }

        System.out.println(message);
    }
}
