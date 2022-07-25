package com.example.clouddemo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RefreshScope
@Data
public class KafkaConfig {

    @Value("${kafka.hosts}")
    private String kafkaHosts;

    @Value("${kafka.producer.topics}")
    private String kafkaTopics;

    public List<String> getKafkaTopicList(){
        List<String> topics = new ArrayList<>();
        String[] arr = kafkaTopics.split(",");
        for(String s : arr){
            topics.add(s);
        }
        return topics;
    }




}
