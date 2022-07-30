package com.example.clouddemo.kafka;

import com.alibaba.fastjson.JSONObject;
import com.example.clouddemo.constant.RedisKeyConstants;
import com.example.clouddemo.kafka.config.KafkaConfig;
import com.example.clouddemo.mybatis.service.MessageService;
import com.example.clouddemo.mybatis.service.MessageServiceImpl;
import com.example.clouddemo.protobuf.ChannelMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Properties;


@AllArgsConstructor
@Slf4j
public class Consumer implements Runnable{
    private KafkaConfig kafkaConfig;
    private RedisTemplate redisTemplate;

    private KafkaConsumer createKafkaConsumer(){
        // 0 配置
        Properties properties = new Properties();

        // 连接 bootstrap.servers
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaConfig.getKafkaHosts());

        // 反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());

        // 配置消费者组id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.getConsumerGroup());

        // 手动提交
        //properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);

        // 1 创建一个消费者  "", "hello"
        KafkaConsumer<String, byte[]> kafkaConsumer = new KafkaConsumer<>(properties);

        // 2 订阅主题 offline_message
        kafkaConsumer.subscribe(kafkaConfig.getKafkaTopicList());
        return kafkaConsumer;
    }

    @Override
    public void run() {
        //一定要先创建好主题,再启动消费者,最后再启动生产者
        int test_counter = 0;
        MessageService service = new MessageServiceImpl();
        KafkaConsumer<String, byte[]> consumer =  createKafkaConsumer();

        //        // 3 消费数据
        while (true){

            ConsumerRecords<String, byte[]> consumerRecords = consumer.poll(Duration.ofSeconds(10));
            log.info("poll number of records = " + consumerRecords.count());
            for (ConsumerRecord<String, byte[]> consumerRecord : consumerRecords) {
                try {
                    ChannelMessage.Message message = ChannelMessage.Message.parseFrom(consumerRecord.value());
//                    service.insert(message.getFromUid(),
//                            message.getToUid(),
//                            message.getBody(),
//                            message.getTimestamp(),
//                            String.valueOf(message.getGlobalSequence()),
//                            String.valueOf(message.getSessionSequence()));
                    String list_key = RedisKeyConstants.OfflineMessageListKeyGenerate(message.getToUid());
                    redisTemplate.opsForList().rightPush(list_key, message.getGlobalSequence() + "");
                    redisTemplate.opsForValue().set(RedisKeyConstants.OfflineMessageGlobalSeqKeyGenerate(message.getGlobalSequence())
                                                    , message2Json(message));

                    log.info("consumer insert a data***********************************************"  + test_counter ++);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("error happen when insert a data======================================");
                }
            }
            //此方法会提交KafkaConsumer#poll() 返回的最新位移
            consumer.commitSync();
        }
    }

    public String message2Json(ChannelMessage.Message message){
        JSONObject jb = new JSONObject();
        jb.put("gs", message.getGlobalSequence() + "");
        jb.put("ss", message.getSessionSequence() + "");
        jb.put("tp", message.getType());
        jb.put("ts", message.getTimestamp());
        jb.put("fu", message.getFromUid());
        jb.put("tu", message.getToUid());
        jb.put("bd", message.getBody());
        return jb.toJSONString();
    }

    public static void main(String[] args) {

    }
}
