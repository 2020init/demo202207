package com.example.clouddemo.ui;

import com.example.clouddemo.constant.MessageTypeConstants;
import com.example.clouddemo.protobuf.ChannelMessage;
import io.netty.channel.ChannelHandlerContext;

import java.util.Random;
import java.util.Scanner;

public class Interactive implements Runnable{
    private ChannelHandlerContext context;
    private long fromUid;

    public Interactive(ChannelHandlerContext context, Long fromUid){
        this.context = context;
        this.fromUid = fromUid;
    }

    public void write(ChannelMessage.Message message){
        if(context == null){
            throw new RuntimeException("context is null");
        }
        context.writeAndFlush(message);
    }

    public void close(){
        context.close();
    }

    public ChannelMessage.Message constructMessage(long toUid, String message){
        ChannelMessage.Message msg = ChannelMessage.Message.newBuilder()
                .setFromUid(String.valueOf(fromUid))
                .setToUid(String.valueOf(toUid))
                .setTimestamp(System.currentTimeMillis())
                .setBody(message)
                .setType(MessageTypeConstants.REQUEST)
                .setGlobalSequence(new Random().nextLong() & 0x7FFFFFFFFFFFFFFFL)
                .setSessionSequence(100000)
                .build();
        return msg;
    }

    public ChannelMessage.Message constructGroupMessage(long group_id, String message){

        ChannelMessage.Message msg = ChannelMessage.Message.newBuilder()
                .setFromUid(String.valueOf(fromUid))
                .setToUid(String.valueOf(group_id))
                .setTimestamp(System.currentTimeMillis())
                .setBody(message)
                .setType(MessageTypeConstants.GROUP_MESSAGE)
                .setGlobalSequence(new Random().nextLong() & 0x7FFFFFFFFFFFFFFFL)
                .setSessionSequence(100000)
                .build();
        return msg;
    }

    public ChannelMessage.Message constructAddGroupMessage(long groupId){
        ChannelMessage.Message msg = ChannelMessage.Message.newBuilder()
                .setFromUid(String.valueOf(fromUid))
                .setToUid(String.valueOf(groupId))
                .setType(MessageTypeConstants.ADD_GROUP)
                .setTimestamp(System.currentTimeMillis())
                .build();
        return msg;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("Please input your option: 1. private message 2. group message  3.add group 4. return");
            int op = scanner.nextInt();
            scanner.nextLine();
            String line;
            switch (op){
                case 1:
                    System.out.println("input: toUid@message");
                    line = scanner.nextLine();
                    String[] ops = line.split("@");
                    ChannelMessage.Message message = constructMessage(Long.parseLong(ops[0]), ops[1]);
                    write(message);
                    break;
                case 2:
                    System.out.println("input: group_id@message");
                    line = scanner.nextLine();
                    ops = line.split("@");
                    message = constructGroupMessage(Long.parseLong(ops[0]) ,ops[1]);
                    write(message);
                    break;
                case 3:
                    System.out.println("input: group_id");
                    line = scanner.nextLine();
                    ops = line.split("@");
                    message = constructAddGroupMessage(Long.parseLong(ops[0]));
                    write(message);
                    break;
                case 4:
                    return;
                default:

            }

        }
    }
}
