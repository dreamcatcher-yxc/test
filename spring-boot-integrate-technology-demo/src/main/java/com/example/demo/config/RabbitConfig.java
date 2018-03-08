//package com.example.demo.config;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitConfig {
//    public static final String SEND_QUEUE_NAME    = "haiguan-queue";
//
//    // 发送到海关时候的路由键, 发送的时候使用
//    public static final String SEND_ROUTING_KEY   = "haiguan-routing-key";
//
//    public static final String RECEIVE_QUEUE_NAME = "qiye-queue";
//
//    // 企业端接收到队列的路由键, 定义 exchang 的时候使用
//    public static final String RECEIVE_ROUTING_KEY = "qiye-routing-key";
//
//    public static final String EXCHANGE_NAME      = "test-exchange";
//
//    @Bean
//    public Queue queue() {
//        return new Queue(SEND_QUEUE_NAME);
//    }
//
//    @Bean
//    public DirectExchange directExchange() {
//        return new DirectExchange(EXCHANGE_NAME, true, false);
//    }
//
//    @Bean
//    public Binding exchangeBinding(@Qualifier("directExchange") DirectExchange directExchange) {
//        return BindingBuilder.bind(new Queue(RECEIVE_QUEUE_NAME)).to(directExchange).with(RECEIVE_ROUTING_KEY);
//    }
//
//    @Bean
//    public RabbitTemplate exchangeTemplate(ConnectionFactory factory) {
//        RabbitTemplate r = new RabbitTemplate(factory);
//        r.setExchange(EXCHANGE_NAME);
//        r.setRoutingKey(SEND_ROUTING_KEY);
//        return r;
//    }
//}
