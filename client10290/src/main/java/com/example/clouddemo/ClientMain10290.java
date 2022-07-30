package com.example.clouddemo;

import com.example.clouddemo.starter.ClientLinkWithConnectorStarter;
import com.example.clouddemo.ui.Interactive;


public class ClientMain10290 {
    public static void main(String[] args) {
        for(int i = 0; i < 100; i ++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new ClientLinkWithConnectorStarter().start(false);
                }
            }).start();

        }
        new ClientLinkWithConnectorStarter().start(true);
    }
}
