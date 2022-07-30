package com.example.clouddemo.cache;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class CopyOnWriteMap {
    private Map<String, VChannel> channelMap = new HashMap<>();
    private List<VChannel> vChannelList = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();

    public ChannelHandlerContext get(String key){
        if(key == null)
            return null;
        Map<String, VChannel> map = channelMap;
        if(!map.containsKey(key)){
            return null;
        }
        return map.get(key).getCtx();
    }

    public List<ChannelHandlerContext> getList(){
        List<ChannelHandlerContext> res = new ArrayList<>();
        for(VChannel vChannel : vChannelList){
            res.add(vChannel.getCtx());
        }
        return res;
    }

    public void put(String key, ChannelHandlerContext ctx, double score){
        if(key == null)
            return;

        final ReentrantLock lock = this.lock;
        lock.lock();
        try{
            List<VChannel> newList = new ArrayList<>();
            Map<String, VChannel> newMap = new HashMap<>();
            for(VChannel vChannel : vChannelList){
                if(vChannel.getChannelId().equals(key)){
                    vChannel.setCtx(ctx);
                    vChannel.setScore(score);
                }
                newMap.put(vChannel.getChannelId(), vChannel);
                newList.add(vChannel);
            }
            if(!newMap.containsKey(key)){
                VChannel channel = new VChannel();
                channel.setScore(score);
                channel.setChannelId(key);
                channel.setCtx(ctx);
                newMap.put(key, channel);
                newList.add(channel);
            }

            sortList(newList);
            setPoints(newMap, newList);
        }finally {
            lock.unlock();
        }
    }

    public void setScore(String key, double value){
        if(key == null)
            return;
        if(!channelMap.containsKey(key)){
            return;
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try{
            //double check
            if(!channelMap.containsKey(key)){
                return;
            }
            VChannel vChannel = channelMap.get(key);
            vChannel.setScore(value);
            sortList(vChannelList);
        }finally {
            lock.unlock();
        }
    }


    public void remove(String key){
        if(key == null)
            return;
        //first check
        if(!channelMap.containsKey(key)){
            return;
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        try{
            //double check
            if(!channelMap.containsKey(key)){
                return;
            }
            List<VChannel> newList = new ArrayList<>();
            Map<String, VChannel> newMap = new HashMap<>();
            for(VChannel vChannel : vChannelList){
                if(vChannel.getChannelId().equals(key)){
                    continue;
                }
                newMap.put(vChannel.getChannelId(), vChannel);
                newList.add(vChannel);
            }
            sortList(newList);
            setPoints(newMap, newList);
        }finally {
            lock.unlock();
        }
    }

    public ChannelHandlerContext getFirst(){
        List<VChannel> list = vChannelList;
        if(list.size() == 0)
            return null;
        return list.get(0).getCtx();
    }

    private void setPoints(Map<String, VChannel> newMap, List<VChannel> newList){
        this.channelMap = newMap;
        this.vChannelList = newList;
    }

    private void sortList(List<VChannel> list){
        Collections.sort(list, new Comparator<VChannel>() {
            @Override
            public int compare(VChannel o1, VChannel o2) {
                if(o2.getScore() - o1.getScore() == 0){
                    return 0;
                }
                return o2.getScore() - o1.getScore() > 0 ? 1 : -1;
            }
        });
    }

}
