package com.example.clouddemo.handler;

import com.example.clouddemo.cache.CopyOnWriteMap;
import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@AllArgsConstructor
public class RequestMessageHandler0 extends SimpleChannelInboundHandler<Message> {
    private CopyOnWriteMap id2transfersMap;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        ChannelMessage.Message message = (ChannelMessage.Message) msg;
        if(message.getType() != MessageTypeConstants.REQUEST){
            ctx.fireChannelRead(msg);
            return;
        }

        ChannelHandlerContext transferCtx = id2transfersMap.getFirst();
        if(transferCtx == null){
            throw new RuntimeException("transferCtx is null");
        }

        message = MessageTypeConstants.setMessageType(message, MessageTypeConstants.NOTIFY);
        transferCtx.writeAndFlush(message);

        ChannelMessage.Message ack = ChannelMessage.Message.newBuilder()
                .setType(MessageTypeConstants.CONNECTOR_ACK)
                .setTimestamp(System.currentTimeMillis())
                .setFromUid(message.getFromUid())
                .setToUid(message.getToUid())
                .setGlobalSequence(message.getGlobalSequence())
                .setSessionSequence(message.getSessionSequence())
                .build();
        ctx.writeAndFlush(ack);
    }
}
