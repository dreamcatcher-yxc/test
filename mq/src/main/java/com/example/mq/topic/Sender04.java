package com.example.mq.topic;

import com.example.mq.util.MQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class Sender04 {

    private static final String EXCHANGE_NAME = "topic_exchange_name";

    public static void main(String[] args) throws IOException {
        if(args.length < 2) {
            System.out.println("at least 2 parameters are required!!!");
            return;
        }
        String routingKey = args[0];
        String message = args[1];

        Connection conn = MQUtil.getConn();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
        System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        channel.close();
        conn.close();
    }
}
