package org.example.hadoop.rpc.server;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;
import org.apache.hadoop.ipc.RPC.Server;
import org.example.hadoop.rpc.common.ILoginService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Builder builder = new RPC.Builder(new Configuration());
        builder.setBindAddress("localhost").setPort(9096).setProtocol(ILoginService.class).setInstance(new LoginServiceImpl());
        Server server = builder.build();
        server.start();

    }
}
