package com.example.mq.basic;

import com.example.mq.util.MQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

public class Receiver01 {

    private static String QUEUE_NAME = "queue02";

    public static void main(String[] args) throws IOException, InterruptedException {
        Connection connection = MQUtil.getConn();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        // autoAck 为 false, 表示需要消息确认。
        channel.basicConsume(QUEUE_NAME, false, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            // 必须确认消息处理完成
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            System.out.println(" [x] Received '" + message + "'");
        }
    }
}
