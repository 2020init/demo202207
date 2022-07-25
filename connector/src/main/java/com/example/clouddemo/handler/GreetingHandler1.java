package com.example.clouddemo.handler;

import com.example.clouddemo.cache.OnlineNumber;
import com.example.clouddemo.config.ConnectorBootstrap;
import com.example.clouddemo.constant.AttributeKeyConstant;
import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.cache.CopyOnWriteMap;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class GreetingHandler1 extends SimpleChannelInboundHandler<Message> {
    private CopyOnWriteMap id2transfersMap;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        log.info("***************************************" + message.toString());
        if(!message.getClass().equals(ChannelMessage.Message.class)){
            return;
        }
        ChannelMessage.Message msg = (ChannelMessage.Message) message;
        if(msg.getType() != MessageTypeConstants.GREETING){
            channelHandlerContext.fireChannelRead(message);
            return;
        }
        String key = msg.getFromUid();
        id2transfersMap.put(key, channelHandlerContext, 1);

        //set a attribute to remember its id
        channelHandlerContext.channel().attr(AttributeKeyConstant.CHANNEL_ID).set(key);

        //record the online number
        OnlineNumber.increase();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelMessage.Message message = ChannelMessage.Message.newBuilder()
                .setFromUid(ConnectorBootstrap.UUID)
                .setTimestamp(System.currentTimeMillis())
                .setType(MessageTypeConstants.GREETING).build();
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if(id2transfersMap != null){
            String key = ctx.channel().attr(AttributeKeyConstant.CHANNEL_ID).get();
            id2transfersMap.remove(key);
            ctx.close();
        }
        //record the online number
        OnlineNumber.decrease();
    }
}
