package com.example.mq.basic;

import com.example.mq.util.MQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

public class Sender01 {

    private static String QUEUE_NAME = "queue02";

    public static void main(String[] args) throws IOException{
        Connection connection = MQUtil.getConn();
        Channel channel = connection.createChannel();

        /**
         * 发送消息
         * durable: 消息是否持久化
         *
         */
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        for(int i = 1; i <=5; i++) {
            String message = i + "";
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }

        // 关闭
        channel.close();
        connection.close();
    }
}
