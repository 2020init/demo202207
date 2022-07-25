package com.example.clouddemo.codec;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MSGEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        //protobuf 生成的java类已经实现了com.google.protobuf.Message
        try {
            //字节流化
            byte[] bytes = msg.toByteArray();

            //字节流长度
            int length = bytes.length;
            //总长度 = 字节流长度 + 4
            ByteBuf buf = Unpooled.buffer(4 + length);
            //写入内容: 字节流长度 + 字节流
            buf.writeInt(length);
            buf.writeBytes(bytes);
            //输出
            out.writeBytes(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
