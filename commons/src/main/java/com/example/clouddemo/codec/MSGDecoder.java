package com.example.clouddemo.codec;

import com.example.clouddemo.protobuf.ChannelMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MSGDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //标记当前读取的位置
        in.markReaderIndex();
        if (in.readableBytes() < 4) {
            in.resetReaderIndex();
            return;
        }
        //读取字节流长度
        int length = in.readInt();

        if (length < 0) {//客户端错误
            ctx.close();
            return;
        }

        //（考虑到TCP拆包）判断是否包含了一个完整的消息
        // 注意 length = 字节流长度, 一个完整的消息包括length字段(已读取) + 字节流
        if (length > in.readableBytes()) {
            in.resetReaderIndex();
            return;
        }

        //读取字节流
        ByteBuf byteBuf = Unpooled.buffer(length);
        in.readBytes(byteBuf);
        byte[] body = byteBuf.array();
        ChannelMessage.Message message = ChannelMessage.Message.parseFrom(body);
        out.add(message);
    }
}
