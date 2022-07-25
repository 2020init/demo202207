package com.example.clouddemo.starter;


import com.example.clouddemo.codec.MSGDecoder;
import com.example.clouddemo.codec.MSGEncoder;
import com.example.clouddemo.config.ClientBootstrap;
import com.example.clouddemo.handler.GreetingHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Order(1)
@Slf4j
public class ClientLinkWithConnectorStarter implements ApplicationRunner {
    @Resource
    private ClientBootstrap clientBootstrap;
    @Resource
    private GreetingHandler greetingHandler;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        start();
    }

    private void start(){
        int port = clientBootstrap.getPort();
        String host = clientBootstrap.getConnector_host();

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
                        p.addLast(greetingHandler);


                    }
                })
                .connect(host, port)
                .addListener((ChannelFutureListener) channelFuture -> {
                    if(channelFuture.isSuccess()){
                        log.info("connect to connector successfully");
                    }else{
                        log.error("connect to connector failed");
                        // reconnect(group, config);
                        throw new RuntimeException("Connect to server fail");
                    }
                });
        try {
            f.get(120, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
