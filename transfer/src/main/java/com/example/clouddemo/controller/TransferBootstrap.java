package com.example.clouddemo.controller;

import cn.hutool.core.util.IdUtil;
import com.example.clouddemo.net.NetAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
@RefreshScope
@Data
public class TransferBootstrap {

    public static final String UUID = IdUtil.randomUUID();
    private int prevVersion = 0;
    private final Map<String, List<NetAddress>> configs = new ConcurrentHashMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Value("${config.version}")
    private int version;

    @Value("${config.transfer}")
    private String transfers;

    @Value("${config.redis}")
    private String redis;

    @Value("${config.kafka}")
    private String kafka;

    @Value("${config.mysql}")
    private String mysql;

    public String getConfig(){
        return getConfigByName("transfer").toString();
    }

    public List<NetAddress> getConfigByName(String name){
        if(isVersionChange(prevVersion)){
            try{
                lock.writeLock().lock();
                if(isVersionChange(prevVersion)){
                    updateConfigs();
                }
            }finally {
                lock.writeLock().unlock();
            }
        }
        return configs.get(name);
    }

    private void updateConfigs(){
        configs.put("transfer", string2NetAddress(transfers));
        configs.put("redis", string2NetAddress(redis));
        configs.put("kafka", string2NetAddress(kafka));
        configs.put("mysql", string2NetAddress(mysql));
    }

    private List<NetAddress> string2NetAddress(String line){
        if(line == null || line.length() <= 0){
            return new ArrayList<>();
        }

        String[] arr = line.split(",");
        List<NetAddress> list = new ArrayList<>();
        for(String s : arr){
            list.add(new NetAddress(Integer.parseInt(s.split(":")[1]), s.split(":")[0]));
        }
        return list;
    }

    public boolean isVersionChange(int version){
        if(this.version > version){
            return true;
        }
        return false;
    }


}
