package com.example.kafkaproducertestdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class KafkaProducer {
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/kafka/test/{msg}")
    public void sendMessage(@PathVariable("msg") String msg) {
        System.out.println("start..." + System.currentTimeMillis());
        kafkaTemplate.send("test", "test",msg.getBytes());
        System.out.println("end..." + System.currentTimeMillis());
    }
}

