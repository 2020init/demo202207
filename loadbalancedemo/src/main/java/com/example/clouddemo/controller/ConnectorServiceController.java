package com.example.clouddemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConnectorServiceController {

    @Value("${connectors.cluster}")
    private String connectorsCluster;

    @GetMapping("/get/connectors/cluster")
    public String getConnectorsCluster(){
        return "localhost:10101";
        //return connectorsCluster;
    }

}
