package com.example.clouddemo.kafka;


import com.example.clouddemo.config.KafkaConfig;
import com.example.clouddemo.protobuf.ChannelMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@AllArgsConstructor
public class MessageProducer implements Runnable{
    private KafkaConfig kafkaConfig;
    private BlockingQueue<ChannelMessage.Message> NotifyMessageBlockingQueue;
    private BlockingQueue<ChannelMessage.Message> AckMessageBlockingQueue;
    private KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer createKafkaProducer(){
        // 0 配置
        Properties properties = new Properties();

        // 连接集群 bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getKafkaHosts());

        // 指定对应的key和value的序列化类型 key.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

        //kafka精确一次配置
        //生产者ack = -1, Leader收集齐ISR中所有broker的ack后才会给生产者发送ack
        properties.put(ProducerConfig.ACKS_CONFIG, "0");
        //开启幂等性和事务
        //properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        //设置transaction id
        //properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "123456");
        //事务在发送时开启

        KafkaProducer<String, byte[]> kafkaProducer = new KafkaProducer(properties);

        return kafkaProducer;
    }

    @Override
    public void run() {
        String topic = kafkaConfig.getKafkaTopics();
        while(true){
            try {
                ChannelMessage.Message message = NotifyMessageBlockingQueue.take();
                kafkaTemplate.send(topic, message.getToUid(), message.toByteArray());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

//    @Override
//    public void run() {
//        int test_count = 0;
//        String topic = kafkaConfig.getKafkaTopics();
//        KafkaProducer<String, byte[]> kafkaProducer = null;
//        kafkaProducer = createKafkaProducer();
//
//        while(true){
//            kafkaProducer = createKafkaProducer();
//            //poll 不会阻塞
//            //take 阻塞
//            try {
//                log.info("blockingQueue take " + test_count ++);
//                ChannelMessage.Message message = NotifyMessageBlockingQueue.take();
//                Future<RecordMetadata> future = kafkaProducer.send(new ProducerRecord<String, byte[]>(topic, message.getFromUid(), message.toByteArray()));
//                //kafkaProducer.close();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//    }

//    public static void main(String[] args) {
//        KafkaProducer<String, byte[]> producer = new MessageProducer().createKafkaProducer();
//        try{
//            long t0 = System.currentTimeMillis();
//            ChannelMessage.Message message = ChannelMessage.Message.newBuilder()
//                    .setType(MessageTypeConstants.ACK_FROM_SERVER)
//                    .setFromUid("123")
//                    .setToUid("456")
//                    .setTimestamp(System.currentTimeMillis())
//                    .setBody("offline")
//                    .setGlobalSequence(100)
//                    .setSessionSequence(101)
//                    .build();
//            for(int i = 100; i > 0; i --){
//                for(int j = 0; j < 20; j ++){
//                    producer.send(new ProducerRecord<String, byte[]>("offline6", "key" + i, message.toByteArray()));
//                }
//            }
//
//            long t1 = System.currentTimeMillis();
//            System.out.println(t1 - t0);
//
//        }catch (Exception e){
//            //producer.abortTransaction();
//            //producer.close();
//            e.printStackTrace();
//        }
//
//    }

}
