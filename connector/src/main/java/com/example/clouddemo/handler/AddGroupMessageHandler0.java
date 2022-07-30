package com.example.clouddemo.handler;


import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.group.Group;
import com.example.clouddemo.group.GroupMember;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Slf4j
public class AddGroupMessageHandler0 extends SimpleChannelInboundHandler<Message> {
    //每个连接都会创建一个自己的AddGroupMessageHandler0, 所以是单线程使用
    private Set<Long> addGroupsSet;//因为是单线程使用, 不必关心线程安全问题
    private Long ownerUid;
    //线程私有的
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
        //获取组
        Group group = Group.getOrDefaultGroupByGroupId(group_id);
        groupMap.put(group_id, group);
        log.info("获取组 " + group_id);
        //将自己加入组
        group.putOnlineMember(uid, groupMember);
        ownerUid = uid;
        addGroupsSet.add(group_id);
        log.info("将" + uid + ",加入组" + group_id);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //当用户下线时, 所在的所有的群组要移除该用户的channel
        //找到用户所在的群组
        //移除用户
        for(Long group_id : addGroupsSet){
            groupMap.get(group_id).deleteOnlineMember(ownerUid);
            log.info("从组" + group_id + "中移除" + ownerUid);
        }
    }
}
