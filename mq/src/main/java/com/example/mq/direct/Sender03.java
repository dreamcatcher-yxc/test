package com.example.mq.direct;

import com.example.mq.util.MQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class Sender03 {

    private static String EXCHANGE_NAME = "direct_exchange_name";

    public static void main(String[] args) throws IOException {
        if(args.length < 2) {
            System.out.println("at least 2 parameters are required!!!");
            return;
        }
        String level = args[0];
        String message = args[1];

        Connection conn = MQUtil.getConn();
        Channel channel = conn.createChannel();
        // 指定为 direct 类型
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        // level 表示 routingKey, 接收端可以根据不同的 routingKey 分别接收自己想要接收到的消息。
        channel.basicPublish(EXCHANGE_NAME, level, null, message.getBytes());
        System.out.println(" [x] Sent '" + level + "':'" + message + "'");

        channel.close();
        conn.close();

    }
}
