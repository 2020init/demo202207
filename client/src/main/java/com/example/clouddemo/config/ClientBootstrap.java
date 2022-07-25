package com.example.clouddemo.config;


import cn.hutool.core.util.IdUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


@Component
@RefreshScope
@Data
public class ClientBootstrap {

    public static final String UUID = "8c09b3f8-ca86-4d75-9e95-37e87bfe78fb";//IdUtil.randomUUID();1cb7cdfd-e63c-47fc-a32e-8b37da83dfcf
    public static final String test_token = "1cb7cdfd-e63c-47fc-a32e-8b37da83dfcf";
    @Value("${connector.host}")
    private String connector_host;
    @Value("${connector.port}")
    private int port;
}
