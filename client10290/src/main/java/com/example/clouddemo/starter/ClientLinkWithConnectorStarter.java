package com.example.clouddemo.starter;

import com.example.clouddemo.codec.MSGDecoder;
import com.example.clouddemo.codec.MSGEncoder;
import com.example.clouddemo.handler.AuthenticHandler;
import com.example.clouddemo.handler.NotifyMessageHandler;
import com.example.clouddemo.ui.Interactive;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ClientLinkWithConnectorStarter {
    private AuthenticHandler authenticHandler = new AuthenticHandler();
    private NotifyMessageHandler notifyMessageHandler = new NotifyMessageHandler();

    public void start(boolean openUI){
        String connector = "localhost:10101";
        /********************TEST******************/
        //LoadBalance lb = new LoadBalance();
        //String connector = lb.getTheBestConnector();

        String host = connector.split(":")[0];
        int port = Integer.parseInt(connector.split(":")[1]);

        //连接服务器
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        ChannelFuture f = b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline p = nioSocketChannel.pipeline();
                        //outbound down to up
                        p.addLast("MSGEncoder", new MSGEncoder());
//
//                            //inbound up to down
                        p.addLast("MSGDecoder", new MSGDecoder());
                        p.addLast(authenticHandler);
                        p.addLast(notifyMessageHandler);
                    }
                })
                .connect(host, port)
                .addListener((ChannelFutureListener) channelFuture -> {
                    if(channelFuture.isSuccess()){
//                        log.info("connect to connector successfully");

                    }else{
//                        log.error("connect to connector failed");
                        // reconnect(group, config);
                        throw new RuntimeException("Connect to server fail");
                    }
                });
        try {
            f.get(120, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }


        if(openUI){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Interactive interactive = new Interactive(authenticHandler.getContext(), Long.parseLong(authenticHandler.getUid()));
            new Thread(interactive).start();
        }


    }
}
