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
public class GroupMessageHandler1 extends SimpleChannelInboundHandler<Message> {
    private Map<Long, Group> groupMap;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        ChannelMessage.Message groupMessage = (ChannelMessage.Message) msg;
        if(groupMessage.getType() != MessageTypeConstants.GROUP_MESSAGE){
            ctx.fireChannelRead(msg);
            return;
        }

        Long group_id = Long.parseLong(groupMessage.getToUid());
        Long from_uid = Long.parseLong(groupMessage.getFromUid());

        //再对连接在该connector上同一个群组的成员进行转发
        Group group = groupMap.get(group_id);
        if(group_id == null)
            return;
        List<GroupMember> groupMemberList = group.getMemberList();
        for(GroupMember groupMember : groupMemberList){
            groupMember.getCtx().writeAndFlush(groupMessage);
        }
    }
}
