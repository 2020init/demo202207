package com.example.clouddemo.netty.starter;

import com.example.clouddemo.cache.CopyOnWriteMap;
import com.example.clouddemo.codec.MSGDecoder;
import com.example.clouddemo.codec.MSGEncoder;
import com.example.clouddemo.config.KafkaConfig;
import com.example.clouddemo.controller.TransferBootstrap;
import com.example.clouddemo.kafka.AckSender;
import com.example.clouddemo.kafka.MessageProducer;
import com.example.clouddemo.netty.handler.AckMessageHandler;
import com.example.clouddemo.netty.handler.GreetingHandler;
import com.example.clouddemo.netty.handler.NotifyMessageHandler;
import com.example.clouddemo.protobuf.ChannelMessage;
import com.example.clouddemo.service.OnlineService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.concurrent.*;

@Component
@Order(1)
@Slf4j
public class TransferStarter implements ApplicationRunner {
    private static final int port = 11111;
    @Resource
    private TransferBootstrap transferBootstrap;
    @Resource
    private CopyOnWriteMap ids2connectorsMap;
    @Resource
    private OnlineService onlineService;
    @Resource
    private KafkaConfig kafkaConfig;

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    private final BlockingQueue<ChannelMessage.Message> notifyMessageBlockingQueue = new LinkedBlockingQueue<ChannelMessage.Message>();
    private final BlockingQueue<ChannelMessage.Message> ackMessageBlockingQueue = new LinkedBlockingQueue<ChannelMessage.Message>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("--------------------------------------------");
        log.info("" + transferBootstrap.getTransfers());
        //ackSenderStart();
        kafkaConsumerStart();
        nettyServerStart();
    }


    private void ackSenderStart(){
        AckSender ackSender = new AckSender(ackMessageBlockingQueue, ids2connectorsMap, onlineService);
        new Thread(ackSender).start();
    }

    private void kafkaConsumerStart(){
        MessageProducer messageProducer = new MessageProducer(kafkaConfig, notifyMessageBlockingQueue, ackMessageBlockingQueue, kafkaTemplate);
        new Thread(messageProducer).start();
    }

    private void nettyServerStart(){

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
                        pipeline.addLast(new GreetingHandler(transferBootstrap, ids2connectorsMap));
                        pipeline.addLast(new NotifyMessageHandler(ids2connectorsMap, onlineService, notifyMessageBlockingQueue));
                        pipeline.addLast(new AckMessageHandler(ids2connectorsMap, onlineService));

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
