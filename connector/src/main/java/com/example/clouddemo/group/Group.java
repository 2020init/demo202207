package com.example.clouddemo.group;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Data
public class Group {
    private final long groupId;
    final AtomicLong listVersion = new AtomicLong(0);
    final AtomicLong mapVersion = new AtomicLong(0);
    private final Map<Long, GroupMember> onlineMember = new HashMap<>();
    List<GroupMember> memberList = new ArrayList<>();

    public void putOnlineMember(Long uid, GroupMember groupMember){
        synchronized (onlineMember){
            onlineMember.put(uid, groupMember);
            mapVersion.incrementAndGet();
        }

    }

    public void deleteOnlineMember(Long uid){

        synchronized (onlineMember){
            onlineMember.remove(uid);
            mapVersion.incrementAndGet();
        }

    }

    public List<GroupMember> getAllOnlineMembers(){
        if(listVersion.get() == mapVersion.get()){
            return memberList;
        }
        synchronized (listVersion){
            if(listVersion.get() != mapVersion.get()){
                synchronized (onlineMember){
                    if(listVersion.get() != mapVersion.get()){
                        List<GroupMember> list = new ArrayList<>();
                        Set<Map.Entry<Long, GroupMember>> set = onlineMember.entrySet();
                        for(Map.Entry<Long, GroupMember> entry : set){
                            list.add(entry.getValue());
                        }
                        memberList = list;
                        listVersion.set(mapVersion.get());
                    }
                }
            }
        }
        return memberList;
    }


    public GroupMember getOnlineMember(Long uid){
        return onlineMember.get(uid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupId == group.groupId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }

    //内聚
    private static final Map<Long, Group> groupMap = new ConcurrentHashMap<>();

    public static Group getOrDefaultGroupByGroupId(long groupId){
        return groupMap.computeIfAbsent(groupId, new Function<Long, Group>() {
            @Override
            public Group apply(Long aLong) {
                return new Group(aLong);
            }
        });
    }

    public static Group getGroupByGroupId(long groupId){
        return groupMap.get(groupId);
    }

}
