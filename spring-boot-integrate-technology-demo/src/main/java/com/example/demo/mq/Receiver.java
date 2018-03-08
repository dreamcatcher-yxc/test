package com.example.demo.mq;

import com.yotexs.settle.external.domain.Foo;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @RabbitListener(queues = "haiguan-queue")
    @RabbitHandler
    public void process(Foo foo) {
        System.out.println("Receiver  : " + foo);
    }

}