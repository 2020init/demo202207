package com.example.clouddemo.redis;

import com.example.clouddemo.constant.RedisKeyConstants;
import com.example.clouddemo.service.OnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class OnlineServiceImpl implements OnlineService {
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public boolean isOnline(String uid) {
        return redisTemplate.opsForHash().hasKey(RedisKeyConstants.ONLINE, uid);
    }

    @Override
    public String getConnectorId(String uid) {
        return (String) redisTemplate.opsForHash().get(RedisKeyConstants.ONLINE, uid);
    }

    @Override
    public void online(String uid, String connectorId) {
        redisTemplate.opsForHash().put(RedisKeyConstants.ONLINE, uid, connectorId);
    }

    @Override
    public void offline(String uid) {
        redisTemplate.opsForHash().delete(RedisKeyConstants.ONLINE, uid);
    }

    @Override
    public void joinGroup(String uid, long group_id){
        String script = "if redis.call('exists', KEYS[1]) == 0\n" +
                "then\n" +
                "    redis.call('sadd', KEYS[1], ARGV[1])\n" +
                "    redis.call('set', KEYS[2], 1)\n" +
                "    redis.call('rpush', KEYS[3], ARGV[2])\n" +
                "    return 1\n" +
                "else\n" +
                "    redis.call('sadd', KEYS[1], ARGV[1])\n" +
                "    redis.call('incr', KEYS[2])\n" +
                "    redis.call('rpush', KEYS[3], ARGV[2])\n" +
                "    local a = redis.call('llen', KEYS[3])\n" +
                "    if a > 500\n" +
                "    then \n" +
                "        redis.call('ltrim', KEYS[3], a - 500, -1) \n" +
                "    end\n" +
                "    return 1\n" +
                "end";

        // 使用redis执行lua执行
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        // 设置一下返回值类型 为Long
        // 因为删除判断的时候，返回的0,给其封装为数据类型。如果不封装那么默认返回String 类型，
        // 那么返回字符串与0 会有发生错误。
        redisScript.setResultType(Long.class);
        // 第一个要是script 脚本 ，第二个需要判断的key，第三个就是key所对应的值。
        String GROUP_VERSION = RedisKeyConstants.GroupVersionKeyGenerate(group_id);
        String GROUP_ONLINE_MEMBER_SET = RedisKeyConstants.GroupOnlineMemberKeyGenerate(group_id);
        String GROUP_EVENT_LIST = RedisKeyConstants.GroupEventListKeyGenerate(group_id);
        redisTemplate.execute(redisScript, Arrays.asList(GROUP_ONLINE_MEMBER_SET, GROUP_VERSION, GROUP_EVENT_LIST), uid, "SADD " + uid);
    }

    @Override
    public void leaveGroup(String uid, long group_id){
        String script = "redis.call('srem', KEYS[1], ARGV[1])\n" +
                "redis.call('incr', KEYS[2])\n" +
                "redis.call('rpush', KEYS[3], ARGV[2])\n" +
                "local a = redis.call('llen', KEYS[3])\n" +
                "if a > 500\n" +
                "then\n" +
                "    redis.call('ltrim', KEYS[3], a - 500, -1)\n" +
                "end\n" +
                "return 1";
        // 使用redis执行lua执行
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        // 设置一下返回值类型 为Long
        redisScript.setResultType(Long.class);
        // 第一个要是script 脚本 ，第二个需要判断的key，第三个就是key所对应的值。
        String GROUP_VERSION = RedisKeyConstants.GroupVersionKeyGenerate(group_id);
        String GROUP_ONLINE_MEMBER_SET = RedisKeyConstants.GroupOnlineMemberKeyGenerate(group_id);
        String GROUP_EVENT_LIST = RedisKeyConstants.GroupEventListKeyGenerate(group_id);
        redisTemplate.execute(redisScript, Arrays.asList(GROUP_ONLINE_MEMBER_SET, GROUP_VERSION, GROUP_EVENT_LIST), uid, "SREM " + uid);
    }
}
