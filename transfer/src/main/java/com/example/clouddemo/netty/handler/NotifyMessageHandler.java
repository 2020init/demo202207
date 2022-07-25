package com.example.clouddemo.netty.handler;

import com.example.clouddemo.cache.CopyOnWriteMap;
import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.example.clouddemo.service.OnlineService;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;

import java.util.concurrent.BlockingQueue;
@AllArgsConstructor
public class NotifyMessageHandler extends SimpleChannelInboundHandler<Message> {
    private CopyOnWriteMap ids2connectorsMap;
    private OnlineService onlineService;
    private BlockingQueue<ChannelMessage.Message> blockingQueue;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        ChannelMessage.Message message = (ChannelMessage.Message) msg;
        if(message.getType() != MessageTypeConstants.NOTIFY){
            ctx.fireChannelRead(message);
            return;
        }
        //get toUid
        String toUid = message.getToUid();
        //find which connector the toUid on
        String connectorId = onlineService.getConnectorId(toUid);
        ChannelHandlerContext connectorCtx = ids2connectorsMap.get(connectorId);
        if(connectorId == null || connectorCtx == null){
            //may be client is offline
            //may be client is on another connector
            //fast notify
//            ChannelMessage.Message ack = ChannelMessage.Message.newBuilder()
//                    .setType(MessageTypeConstants.CANNOT_REACH)
//                    .setTimestamp(System.currentTimeMillis())
//                    .setFromUid(message.getToUid())
//                    .setToUid(message.getFromUid())
//                    .setGlobalSequence(message.getGlobalSequence())
//                    .setSessionSequence(message.getSessionSequence())
//                    .build();
//            ctx.writeAndFlush(ack);

            //store offline message
            blockingQueue.offer(message);
            return;
        }
        connectorCtx.writeAndFlush(message);




    }
}
