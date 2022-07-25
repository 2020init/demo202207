package com.example.clouddemo.handler;

import com.example.clouddemo.cache.CopyOnWriteMap;
import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.group.Group;
import com.example.clouddemo.group.GroupMember;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
public class GroupMessageHandler0 extends SimpleChannelInboundHandler<Message> {
    private Map<Long, Group> groupMap;
    private CopyOnWriteMap id2transfersMap;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        ChannelMessage.Message message = (ChannelMessage.Message) msg;
        if(message.getType() != MessageTypeConstants.GROUP_MESSAGE){
            ctx.fireChannelRead(msg);
            return;
        }

        Long group_id = Long.parseLong(message.getToUid());
        Long from_uid = Long.parseLong(message.getFromUid());

        //先转发给transfer
        ChannelHandlerContext transferCtx = id2transfersMap.getFirst();
        transferCtx.writeAndFlush(msg);

        //再对连接在该connector上同一个群组的成员进行转发
        Group group = groupMap.get(group_id);
        List<GroupMember> groupMemberList = group.getMemberList();
        for(GroupMember groupMember : groupMemberList){
            if(groupMember.getUid() == from_uid)
                continue;
            groupMember.getCtx().writeAndFlush(message);
        }

    }
}
