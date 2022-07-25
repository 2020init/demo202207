package com.example.clouddemo.net;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NetAddress{
    private final int port;
    private final String ip;
}
