package com.example.clouddemo.controller;


import com.example.clouddemo.cache.OnlineNumber;
import com.example.clouddemo.service.OnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnlineNumberController {
    @Autowired
    private OnlineService onlineService;

    @GetMapping("/get/onlinenumber")
    public String getOnlineNumber(){
        return OnlineNumber.get() + "";
    }

    @GetMapping("/join/group/{uid}/{group_id}")
    public String joinGroupTest(@PathVariable("uid") String uid, @PathVariable("group_id") Long group_id){
        onlineService.joinGroup(uid, group_id);
        return "ok";
    }

    @GetMapping("/leave/group/{uid}/{group_id}")
    public String leaveGroupTest(@PathVariable("uid") String uid, @PathVariable("group_id") Long group_id){
        onlineService.leaveGroup(uid, group_id);
        return "ok";
    }
}
