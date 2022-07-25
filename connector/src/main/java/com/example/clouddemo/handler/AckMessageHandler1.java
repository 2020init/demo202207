package com.example.clouddemo.handler;

import com.example.clouddemo.cache.SimpleMap;
import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AckMessageHandler1 extends SimpleChannelInboundHandler<Message> {
    private SimpleMap ids2clientsMap;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        ChannelMessage.Message message = (ChannelMessage.Message) msg;
        if(message.getType() != MessageTypeConstants.ACK_FROM_SERVER &&
                message.getType() != MessageTypeConstants.CANNOT_REACH &&
                message.getType() != MessageTypeConstants.ACK_FROM_CLIENT){
            ctx.fireChannelRead(msg);
            return;
        }
        String toUid = message.getToUid();
        ChannelHandlerContext clientCtx = ids2clientsMap.get(toUid);
        if(clientCtx == null)
            return;
        clientCtx.writeAndFlush(message);
    }
}
