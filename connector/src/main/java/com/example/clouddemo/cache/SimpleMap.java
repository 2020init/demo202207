package com.example.clouddemo.cache;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SimpleMap {
    private Map<String, VChannel> vChannelMap = new ConcurrentHashMap<>();

    public ChannelHandlerContext put(String key, ChannelHandlerContext ctx){
        if(key == null)
            return null;
        VChannel channel = new VChannel();
        channel.setChannelId(key);
        channel.setCtx(ctx);
        VChannel old = vChannelMap.put(key, channel);
        if(old != null){
            return old.getCtx();
        }
        return null;
    }

    public ChannelHandlerContext remove(String key){
        if(key == null){
            return null;
        }
        VChannel channel = vChannelMap.remove(key);
        if(channel == null){
            return null;
        }
        return channel.getCtx();
    }

    public ChannelHandlerContext get(String key){
        if(key == null){
            return null;
        }
        VChannel channel = vChannelMap.get(key);
        if(channel != null){
            return channel.getCtx();
        }
        return null;
    }

}
