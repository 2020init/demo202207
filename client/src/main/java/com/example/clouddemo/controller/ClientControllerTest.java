package com.example.clouddemo.controller;


import com.example.clouddemo.config.ClientBootstrap;
import com.example.clouddemo.handler.GreetingHandler;
import com.example.clouddemo.starter.ClientLinkWithConnectorStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;
import java.util.UUID;

@RestController
public class ClientControllerTest {

    @Resource
    private GreetingHandler greetingHandler;

    @GetMapping("/send/{toUid}/{content}")
    public String send(@PathVariable("toUid") String toUid,
                       @PathVariable("content") String content){

        int i = 0;
        for(; i < 100; i ++){
            greetingHandler.write(content, i + "", ClientBootstrap.UUID, System.currentTimeMillis() - new Random().nextInt(1000000));
        }

        return "sending...";
    }
}
