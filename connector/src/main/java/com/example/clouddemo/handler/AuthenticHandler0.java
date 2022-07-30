package com.example.clouddemo.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.clouddemo.cache.SimpleMap;
import com.example.clouddemo.config.ConnectorBootstrap;
import com.example.clouddemo.constant.AttributeKeyConstant;
import com.example.clouddemo.constant.MessageKeyConstants;
import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.example.clouddemo.service.OnlineService;
import com.example.clouddemo.service.TokenService;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AuthenticHandler0 extends SimpleChannelInboundHandler<Message> {
    private SimpleMap ids2clientsMap;
    private TokenService tokenService;
    private OnlineService onlineService;


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        log.info("" + message.toString());
        //has auth
        if(channelHandlerContext.channel().attr(AttributeKeyConstant.CHANNEL_ID).get() != null){
            channelHandlerContext.fireChannelRead(message);
            return;
        }

        if(ids2clientsMap == null || !message.getClass().equals(ChannelMessage.Message.class)){
            channelHandlerContext.fireChannelRead(message);
            return;
        }

        //first auth
        ChannelMessage.Message msg = (ChannelMessage.Message) message;
        if(msg.getType() != MessageTypeConstants.AUTH){
            //unauthentic
            channelHandlerContext.close();
            return;
        }

        //get token and uid
        String id = msg.getFromUid();
        String token = msg.getBody();

        if(!tokenService.checkTokenAndUid(id, token)){
            //unauthentic
            log.info("unauthentic user : " + id + ":" + token);
            return;
        }
        ids2clientsMap.put(id, channelHandlerContext);
        onlineService.online(id, ConnectorBootstrap.UUID);

        //set a attribute to remember its id
        channelHandlerContext.channel().attr(AttributeKeyConstant.CHANNEL_ID).set(id);
        log.info("auth success " + id);
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
        String id = ctx.channel().attr(AttributeKeyConstant.CHANNEL_ID).get();
        if(id == null)
            return;
        log.info("移除" + id + "的ctx");
        onlineService.offline(id);
        ids2clientsMap.remove(id);

    }
}
