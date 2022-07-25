package com.example.clouddemo;

import com.example.clouddemo.kafka.Consumer;
import com.example.clouddemo.kafka.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Slf4j
public class ConsumerStarter implements ApplicationRunner {
    @Autowired
    private KafkaConfig kafkaConfig;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        for(int i = 0; i < 3; i ++){
            Consumer consumer = new Consumer(kafkaConfig, redisTemplate);
            log.info("***************************************");
            new Thread(consumer).start();
        }
    }
}
