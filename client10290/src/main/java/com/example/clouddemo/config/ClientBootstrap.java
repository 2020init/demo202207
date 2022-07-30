package com.example.clouddemo.config;

import java.util.Random;

public class ClientBootstrap {
    public static final Random random = new Random(System.currentTimeMillis());
    //public static final String UUID = String.valueOf((new Random(System.currentTimeMillis()).nextLong()) & 0x7FFFFFFFFFFFFFFFL);//IdUtil.randomUUID();1cb7cdfd-e63c-47fc-a32e-8b37da83dfcf
    public static final String test_token = String.valueOf((new Random(System.currentTimeMillis()).nextLong()) & 0x7FFFFFFFFFFFFFFFL);

    public static String getUuid(){
        return "" + (random.nextLong() & 0x7FFFFFFFFFFFFFFFL);
    }


}
