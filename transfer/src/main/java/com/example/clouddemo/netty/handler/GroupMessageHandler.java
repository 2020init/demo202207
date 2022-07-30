package com.example.clouddemo.netty.handler;

import com.example.clouddemo.cache.CopyOnWriteMap;
import com.example.clouddemo.constant.AttributeKeyConstant;
import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.BlockingQueue;


@AllArgsConstructor
@Slf4j
public class GroupMessageHandler extends SimpleChannelInboundHandler<Message> {
    private BlockingQueue<ChannelMessage.Message> blockingQueue;
    private CopyOnWriteMap ids2connectorsMap;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        ChannelMessage.Message message = (ChannelMessage.Message) msg;
        if(message.getType() != MessageTypeConstants.GROUP_MESSAGE){
            ctx.fireChannelRead(message);
            return;
        }
        //将消息转发给其他connector
        String cid = ctx.channel().attr(AttributeKeyConstant.CHANNEL_ID).get();
        log.info("收到" + cid + "发送的群消息" + message.getGlobalSequence());
        List<ChannelHandlerContext> list = ids2connectorsMap.getList();
        for(ChannelHandlerContext chc : list){
            if(cid.equals(chc.channel().attr(AttributeKeyConstant.CHANNEL_ID).get())){
                continue;
            }
            chc.writeAndFlush(message);
            log.info("将群消息" + message.getGlobalSequence() + "转发给connector");
        }
        //写入离线消息缓存
        blockingQueue.offer(message);
        log.info("将群消息" + message.getGlobalSequence() + "写入缓存");
    }
}
