package com.example.clouddemo.message;

import com.example.clouddemo.protobuf.ChannelMessage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class WaitingList implements AckWait{
    private List<ChannelMessage.Message> messageList = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void put(ChannelMessage.Message message) {
        lock.lock();
        try{
            messageList.add(message);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void remove(ChannelMessage.Message ack) {
        lock.lock();
        try{
            int index = 0;
            for(ChannelMessage.Message message : messageList){
                if(message.getToUid().equals(ack.getFromUid()) && message.getGlobalSequence() == ack.getGlobalSequence()){
                    messageList.remove(index);
                    break;
                }
                index ++;
            }
        }finally {
            lock.unlock();
        }
    }

    @Override
    public List<ChannelMessage.Message> timeOutResend(long theEarlierTime) {
        lock.lock();
        try{
            List<ChannelMessage.Message> resend = new LinkedList<>();
            for(ChannelMessage.Message message : messageList){
                if(message.getTimestamp() < theEarlierTime){
                    resend.add(message);
                }
            }
            return resend;
        }finally {
            lock.unlock();
        }


    }

    @Override
    public List<ChannelMessage.Message> giveUpResend(long theEarlierTime) {
        lock.lock();
        try{
            List<ChannelMessage.Message> giveUp = new LinkedList<>();
            List<ChannelMessage.Message> rest = new LinkedList<>();
            int index = 0;
            for(ChannelMessage.Message message : messageList){
                if(message.getTimestamp() > theEarlierTime){
                    rest.add(message);
                }else{
                    giveUp.add(message);
                }
            }
            messageList = rest;
            return giveUp;
        }finally {
            lock.unlock();
        }


    }
}
