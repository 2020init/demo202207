package com.example.clouddemo.handler;

import com.example.clouddemo.cache.SimpleMap;
import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotifyMessageHandler1 extends SimpleChannelInboundHandler<Message> {
    private SimpleMap ids2clientsMap;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        ChannelMessage.Message message = (ChannelMessage.Message) msg;
        if(message.getType() != MessageTypeConstants.NOTIFY){
            ctx.fireChannelRead(msg);
            return;
        }
        //check whether the client is on here
        String toUid = message.getToUid();
        ChannelHandlerContext clientCtx = ids2clientsMap.get(toUid);
        if(clientCtx == null){
            //may be client is offline
            //may be client is on another connector
            //fast notify
            ChannelMessage.Message ack = ChannelMessage.Message.newBuilder()
                    .setType(MessageTypeConstants.CANNOT_REACH)
                    .setTimestamp(System.currentTimeMillis())
                    .setFromUid(message.getToUid())
                    .setToUid(message.getFromUid())
                    .setGlobalSequence(message.getGlobalSequence())
                    .setSessionSequence(message.getSessionSequence())
                    .build();
            ctx.writeAndFlush(ack);
            return;
        }
        clientCtx.writeAndFlush(message);

    }
}
