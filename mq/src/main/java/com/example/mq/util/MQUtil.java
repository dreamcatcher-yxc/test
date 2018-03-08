package com.example.mq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class MQUtil {

    private static ConnectionFactory FACTORY;

    static {
        FACTORY = new ConnectionFactory();
        FACTORY.setHost("172.16.9.106");
        FACTORY.setPort(5672);
        FACTORY.setUsername("admin");
        FACTORY.setPassword("x");
    }

    public static Connection getConn() throws IOException{
        return  FACTORY.newConnection();
    }
}
