package com.example.clouddemo.kafka;

import com.example.clouddemo.cache.CopyOnWriteMap;
import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.example.clouddemo.service.OnlineService;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;


@Slf4j
@AllArgsConstructor
public class AckSender implements Runnable{
    private BlockingQueue<ChannelMessage.Message> ackMessageBlockingQueue;
    private CopyOnWriteMap ids2connectorsMap;
    private OnlineService onlineService;


    @Override
    public void run() {
        while(true){
            try{
                ChannelMessage.Message message = ackMessageBlockingQueue.take();
                //get fromUid, because it is ack
                String toUid = message.getFromUid();
                //find which connector the toUid on
                String connectorId = onlineService.getConnectorId(toUid);
                ChannelHandlerContext connectorCtx = ids2connectorsMap.get(connectorId);
                connectorCtx.writeAndFlush(ChannelMessage.Message
                        .newBuilder(message)
                        .setBody("")
                        .setType(MessageTypeConstants.ACK_FROM_SERVER)
                        .setFromUid(message.getToUid())
                        .setToUid(message.getFromUid())
                );

            }catch (Exception e){
                log.error("AckSender" + Thread.currentThread().getName() + ": exception happens--> " + ackMessageBlockingQueue.size());
            }
        }
    }

    public static void main(String[] args) {
        ChannelMessage.Message message = ChannelMessage.Message.newBuilder()
                .setType(MessageTypeConstants.ACK_FROM_SERVER)
                .setFromUid("wwwww")
                .setToUid("wwwwww")
                .setTimestamp(System.currentTimeMillis())
                .setToUid("wwwwww")
                .setBody("wwwww")
                .build();
        ChannelMessage.Message msg = ChannelMessage.Message.newBuilder(message)
                .setBody("")
                .setType(MessageTypeConstants.ACK_FROM_SERVER)
                .build();
        System.out.println((message.toByteArray().length));

    }
}
