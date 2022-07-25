package com.example.clouddemo.cache;

import java.util.concurrent.atomic.AtomicLong;

public class OnlineNumber {
    private static final AtomicLong onlineNumber = new AtomicLong(0);

    public static long get(){
        return onlineNumber.get();
    }

    public static void increase(){
        onlineNumber.incrementAndGet();
    }

    public static void decrease(){
        onlineNumber.incrementAndGet();
    }
}
