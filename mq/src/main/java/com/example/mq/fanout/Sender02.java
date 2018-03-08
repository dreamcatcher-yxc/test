package com.example.mq.fanout;

import com.example.mq.util.MQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class Sender02 {

    private static final String EXCHANGE_NAME = "fanout_exchange_name";

    public static void main(String[] args)  throws IOException, InterruptedException {
        String message = "this is a message";
        Connection conn = MQUtil.getConn();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        conn.close();
    }
}
