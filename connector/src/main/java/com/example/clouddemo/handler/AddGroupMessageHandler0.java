package com.example.clouddemo.handler;


import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.group.Group;
import com.example.clouddemo.group.GroupMember;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@AllArgsConstructor
public class AddGroupMessageHandler0 extends SimpleChannelInboundHandler<Message> {
    private Set<Long> addGroupsSet = new HashSet<>();//因为是单线程使用, 不必关心线程安全问题
    private Long ownerUid;
    private Map<Long, Group> groupMap;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        ChannelMessage.Message message = (ChannelMessage.Message) msg;
        if(message.getType() != MessageTypeConstants.ADD_GROUP){
            ctx.fireChannelRead(msg);
            return;
        }

        long group_id = Long.parseLong(message.getToUid());
        long uid = Long.parseLong(message.getFromUid());
        GroupMember groupMember = new GroupMember(uid, ctx);
        Group group = groupMap.computeIfAbsent(group_id, new Function<Long, Group>() {
            @Override
            public Group apply(Long aLong) {
                return new Group(group_id);
            }
        });
        group.putOnlineMember(uid, groupMember);
        ownerUid = uid;
        addGroupsSet.add(group_id);


    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //当用户下线时, 所在的所有的群组要移除该用户的channel
        //找到用户所在的群组
        //移除用户
        for(Long group_id : addGroupsSet){
            groupMap.get(group_id).deleteOnlineMember(ownerUid);
        }
    }
}
