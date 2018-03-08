package com.example.mq.topic;

import com.example.mq.util.MQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

public class Receiver04 {

    private static final String EXCHANGE_NAME = "topic_exchange_name";

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length < 1) {
            System.out.println("at least 1 parameters are required!!!");
            return;
        }
        String routingKey = args[0];

        Connection conn = MQUtil.getConn();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            routingKey = delivery.getEnvelope().getRoutingKey();
            System.out.println(" [x] Received '" + routingKey + "':'" + message + "'");
        }
    }
}
