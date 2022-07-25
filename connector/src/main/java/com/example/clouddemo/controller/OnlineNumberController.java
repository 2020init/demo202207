package com.example.clouddemo.controller;


import com.example.clouddemo.cache.OnlineNumber;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnlineNumberController {

    @GetMapping("/get/onlinenumber")
    public String getOnlineNumber(){
        return OnlineNumber.get() + "";
    }
}
