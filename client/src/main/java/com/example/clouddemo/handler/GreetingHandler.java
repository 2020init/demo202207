package com.example.clouddemo.handler;

import com.example.clouddemo.config.ClientBootstrap;
import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ChannelHandler.Sharable
public class GreetingHandler extends SimpleChannelInboundHandler<Message> {
    private ChannelHandlerContext channelHandlerContext;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        log.info("***************************************" + message.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelHandlerContext = ctx;

        ChannelMessage.Message message = ChannelMessage.Message.newBuilder()
                .setFromUid(ClientBootstrap.UUID)
                .setBody(ClientBootstrap.test_token)//token
                .setTimestamp(System.currentTimeMillis())
                .setType(MessageTypeConstants.AUTH)
                .build();
        ctx.writeAndFlush(message);
    }

    public void close(){
        if(channelHandlerContext != null){
            channelHandlerContext.close();
        }
    }

    public void write(String content, String toUid, String fromUid, long global_seq){
        ChannelMessage.Message message = ChannelMessage.Message.newBuilder()
                .setSessionSequence(100)
                .setGlobalSequence(global_seq)
                .setTimestamp(System.currentTimeMillis())
                .setBody(content)
                .setType(MessageTypeConstants.REQUEST)
                .setFromUid(fromUid)
                .setToUid(toUid)
                .build();
        channelHandlerContext.writeAndFlush(message);
    }
}
