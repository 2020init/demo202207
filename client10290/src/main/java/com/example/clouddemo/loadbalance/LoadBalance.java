package com.example.clouddemo.loadbalance;


import com.example.clouddemo.okhttp.HttpUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Data
public class LoadBalance {
    private String[] connectors;
    private String url = "http://localhost:10699/get/connectors/cluster";

    public static void main(String[] args) {
        LoadBalance lb = new LoadBalance();
        lb.getTheBestConnector();
    }


    public String getTheBestConnector(){
        //首先去获取服务器
        HttpUtils httpUtils = new HttpUtils();
        String[] connectors = httpUtils.httpGet(url).split(",");
        List<Long> time = new ArrayList<>();
        List<Cost> costs = new ArrayList<>();
        //获取服务器上的在线人数
        long[] numbers = new long[connectors.length];
        for(int i = 0; i < numbers.length; i ++){
            Cost cost = new Cost();
            cost.url = connectors[i];
            long t0 = System.currentTimeMillis();
            String res = httpUtils.httpGet("http://" + connectors[i].split(":")[0] + "/get/onlinenumber");
            long t1 = System.currentTimeMillis();
            time.add(t1 - t0);
            cost.timeCost = t1 - t0;
            cost.onlineNumber = Long.parseLong(res);
            costs.add(cost);
        }
        //排序,对在线人数和时间进行打分
        Collections.sort(costs, new Comparator<Cost>() {
            @Override
            public int compare(Cost o1, Cost o2) {
                return (int) (((o1.timeCost - o2.timeCost) << 14) + (o1.onlineNumber - o2.onlineNumber));
            }
        });
        return costs.get(0).url;
    }


    private static void sort(long[] numbers, String[] ss, int start, int end){
        if(start >= end){
            return;
        }
        long temp = numbers[start];
        String stemp = ss[start];
        int left = start;
        int right = end;
        while(left < right){
            while(left < right && numbers[right] >= temp){
                right --;
            }
            numbers[left] = numbers[right];
            ss[left] = ss[right];
            numbers[right] = temp;
            ss[right] = stemp;
            while(left < right && numbers[left] < temp){
                left ++;
            }
            numbers[right] = numbers[left];
            numbers[left] = temp;
            ss[right] = ss[left];
            ss[left] = stemp;
        }
        ss[left] = stemp;
        numbers[left] = temp;
        sort(numbers, ss, start, left - 1);
        sort(numbers, ss, left + 1, end);
    }

}

class Cost{
    long timeCost;
    String url;
    long onlineNumber;
}
