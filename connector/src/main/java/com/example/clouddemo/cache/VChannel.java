package com.example.clouddemo.cache;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VChannel {
    private String channelId;
    private ChannelHandlerContext ctx;
    private double score;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VChannel vChannel = (VChannel) o;
        return vChannel.getChannelId() != null && vChannel.getChannelId().equals(channelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelId);
    }
}
