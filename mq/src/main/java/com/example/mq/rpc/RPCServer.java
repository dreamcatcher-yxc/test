package com.example.mq.rpc;

import com.example.mq.util.MQUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

public class RPCServer {
    private static final String RPC_QUEUE_NAME = "rpc_queue";

    public static void main(String[] args) throws IOException, InterruptedException {
        Connection conn = MQUtil.getConn();
        Channel channel = conn.createChannel();
        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        // 配置在服务器没有返回接收到的消息的确认消息的时候，队列都不会向它发送其他的消息
        channel.basicQos(1);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            AMQP.BasicProperties props = delivery.getProperties();
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
                    .correlationId(props.getCorrelationId())
                    .build();
            String message = new String(delivery.getBody());
            int n = Integer.parseInt(message);
            System.out.println(" [.] fib(" + message + ")");
            String response = "" + fib(n);
            channel.basicPublish( "", props.getReplyTo(), replyProps, response.getBytes());
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

    private static long fib(int n) {
        if(n < 3) {
            return 1;
        } else if(n == 3) {
            return 2;
        } else {
            long a = 1, b = 2, c = 3;
            for(int i = 0; i < n - 3; i++) {
                c = a + b;
                a = b;
                b = c;
            }
            return c;
        }
    }
}
