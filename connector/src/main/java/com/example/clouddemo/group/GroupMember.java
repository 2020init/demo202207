package com.example.clouddemo.group;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;


@Data
@AllArgsConstructor
public class GroupMember {
    private final long uid;
    private ChannelHandlerContext ctx;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMember that = (GroupMember) o;
        return uid == that.uid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }
}
