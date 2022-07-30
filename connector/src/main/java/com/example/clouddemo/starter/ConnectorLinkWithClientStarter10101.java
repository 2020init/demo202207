package com.example.clouddemo.starter;

import com.example.clouddemo.cache.CopyOnWriteMap;
import com.example.clouddemo.cache.SimpleMap;
import com.example.clouddemo.codec.MSGDecoder;
import com.example.clouddemo.codec.MSGEncoder;
import com.example.clouddemo.config.ConnectorBootstrap;
import com.example.clouddemo.group.Group;
import com.example.clouddemo.handler.*;
import com.example.clouddemo.service.OnlineService;
import com.example.clouddemo.service.TokenService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Order(2)
@Slf4j
public class ConnectorLinkWithClientStarter10101 implements ApplicationRunner {
    private static final int port = 10101;
    @Autowired
    private ConnectorBootstrap connectorBootstrap;
    @Resource(name = "tokenServiceTestImpl")
    private TokenService tokenService;
    @Autowired
    private OnlineService onlineService;
    @Autowired
    private SimpleMap ids2clientsMap;
    @Autowired
    private CopyOnWriteMap ids2transfersMap;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("--------------------------------------------");
        start();
    }

    private void start(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
//                        //outbound 执行顺序从下往上
                        pipeline.addLast("MSGEncoder", new MSGEncoder());
//
//                        //inbound 执行顺序从上往下
                        pipeline.addLast("MSGDecoder", new MSGDecoder());
                        pipeline.addLast(new AuthenticHandler0(ids2clientsMap, tokenService, onlineService));
                        pipeline.addLast(new AckMessageHandler0(ids2transfersMap));
                        pipeline.addLast(new RequestMessageHandler0(ids2transfersMap));

                        Map<Long, Group> groupMap = new HashMap<>();
                        pipeline.addLast(new GroupMessageHandler0(groupMap, ids2transfersMap));
                        pipeline.addLast(new AddGroupMessageHandler0(new HashSet<>(), -1L, groupMap));

                    }
                });
        ChannelFuture f = bootstrap.bind(new InetSocketAddress(port)).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                log.info("connector listen in port : " + port);
            } else {
                throw new RuntimeException("[transfer] start failed");
            }
        });
        try {
            f.get(120, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException("[transfer] start failed", e);
        }
    }


}
