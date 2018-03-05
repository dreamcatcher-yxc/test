package org.example.hadoop.rpc.client;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.example.hadoop.rpc.common.ILoginService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException{
        ILoginService proxy = RPC.getProxy(ILoginService.class, 1L, new InetSocketAddress("localhost", 9096), new Configuration());

        String result = proxy.login("mijie", "123456");

        System.out.println(result);
    }
}
