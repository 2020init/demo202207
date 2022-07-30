package com.example.clouddemo.starter;

import com.example.clouddemo.cache.SimpleMap;
import com.example.clouddemo.codec.MSGDecoder;
import com.example.clouddemo.codec.MSGEncoder;
import com.example.clouddemo.config.ConnectorBootstrap;
import com.example.clouddemo.handler.AckMessageHandler1;
import com.example.clouddemo.handler.GroupMessageHandler1;
import com.example.clouddemo.handler.NotifyMessageHandler1;
import com.example.clouddemo.net.NetAddress;
import com.example.clouddemo.handler.GreetingHandler1;
import com.example.clouddemo.cache.CopyOnWriteMap;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Component
@Order(1)
@Slf4j
public class ConnectorLinkWithTransferStarter implements ApplicationRunner {

    @Autowired
    private CopyOnWriteMap ids2transfersMap;
    @Autowired
    private SimpleMap ids2clientsMap;
    @Resource
    private ConnectorBootstrap connectorBootstrap;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        connect();
    }

    public void connect(){

        List<NetAddress> transferLists = connectorBootstrap.getConfigByName("transfer");
        log.info("----------" + transferLists.toString());
        for(NetAddress netAddress : transferLists){
            String host = netAddress.getIp();
            int port = netAddress.getPort();

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
                            p.addLast(new GreetingHandler1(ids2transfersMap));
                            p.addLast(new AckMessageHandler1(ids2clientsMap));
                            p.addLast(new NotifyMessageHandler1(ids2clientsMap));
                            p.addLast(new GroupMessageHandler1());
                        }
                    })
                    .connect(host, port)
                    .addListener((ChannelFutureListener) channelFuture -> {
                        if(channelFuture.isSuccess()){
                            log.info("connect to transfer " + host + ":" + port + " successfully");
                        }else{
                            log.error("connect to transfer " + host + ":" + port + " failure");
                            // throw new RuntimeException("Connect to server fail");
                        }
                    });
            try {
                f.get(120, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }




    }


}
