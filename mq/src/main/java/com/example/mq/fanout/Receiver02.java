package com.example.mq.fanout;

import com.example.mq.util.MQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

public class Receiver02 {
    private static String EXCHANGE_NAME = "fanout_exchange_name";

    public static void main(String[] args)  throws IOException, InterruptedException {
        Connection conn = MQUtil.getConn();
        Channel channel = conn.createChannel();
        // fanout, 广播模式，所有的消费者都会接收到发送的消息
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        // 等待接收消息
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        }

    }
}
