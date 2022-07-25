package com.example.clouddemo.netty.handler;

import com.example.clouddemo.cache.CopyOnWriteMap;
import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.example.clouddemo.service.OnlineService;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class AckMessageHandler extends SimpleChannelInboundHandler<Message> {
    private CopyOnWriteMap ids2connectorsMap;
    private OnlineService onlineService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        ChannelMessage.Message message = (ChannelMessage.Message) msg;
        if(message.getType() != MessageTypeConstants.ACK_FROM_CLIENT){
            ctx.fireChannelRead(message);
            return;
        }
        String toUid = message.getToUid();
        String connectorId = onlineService.getConnectorId(toUid);
        ChannelHandlerContext connectorCtx = ids2connectorsMap.get(connectorId);
        if(connectorId == null || connectorCtx == null)
            return;
        connectorCtx.writeAndFlush(message);
    }
}
