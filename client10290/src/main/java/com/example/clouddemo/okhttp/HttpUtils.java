package com.example.clouddemo.okhttp;

import com.sun.org.apache.xerces.internal.impl.io.UTF8Reader;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class HttpUtils {
    private OkHttpClient okHttpClient;
    private String url;

    public HttpUtils(){
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .callTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    public String httpGet(String url){
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            okHttpClient.pingIntervalMillis();
            return okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Long.MAX_VALUE + "";
    }

    public static int ping(String ipAddress){
        String lastLine = "";
        try{
            String line = null;
            Process pro = Runtime.getRuntime().exec("ping " + ipAddress);
            BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream(), "UTF-8"));
            while ((line = buf.readLine()) != null){
                System.out.println(line);
                lastLine = line;
            }
            //最短 = 0ms，最长 = 0ms，平均 = 0ms
            String[] numbers =lastLine.split("=");
            String res = numbers[numbers.length - 1].trim();
            return Integer.parseInt(res.substring(0, res.length() - 2));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return Integer.MAX_VALUE;
    }

    public static void main(String[] args) {
        int i = ping("176.0.1.21");
        System.out.println(i);
    }

//    public static void main(String[] args) {
//        HttpUtils httpUtils = new HttpUtils();
//        String res = httpUtils.httpGet("http://localhost:10699/get/connectors/cluster");
//        System.out.println(res);
//    }
}
