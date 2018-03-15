package com.example.demo.mq;

import com.example.demo.entity.p1.Foo01;
import com.example.demo.entity.p2.Foo02;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.example.demo.config.RabbitConfig.*;

@Component
public class MessageListener {

    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(name = FNAOUT_EXCHANGE_NAME, type = ExchangeTypes.FANOUT), value = @Queue))
    public void fanoutExchangeHandler01(String msg) {
        System.out.println("fanoutExchangeHandler01 receive msg: " + msg);
    }

    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(name = FNAOUT_EXCHANGE_NAME, type = ExchangeTypes.FANOUT), value = @Queue))
    public void fanoutExchangeHandler02(String msg) {
        System.out.println("fanoutExchangeHandler02 receive msg: " + msg);
    }

    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(name = DIRECT_EXCHANGE_NAME, type = ExchangeTypes.DIRECT), value = @Queue, key = "warn"))
    public void directExchangeHandler01(String msg) {
        System.out.println("directExchangeHandler01 receive msg: " + msg);
    }

    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(name = DIRECT_EXCHANGE_NAME, type = ExchangeTypes.DIRECT), value = @Queue, key = "error"))
    public void directExchangeHandler02(String msg) {
        System.out.println("directExchangeHandler02 receive msg: " + msg);
    }

    /**
     * 接收对象需要保证可序列化, 序列号相同, 并且类结构一直, 包名一致.
     * @param foo01
     */
    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(name = DIRECT_EXCHANGE_NAME, type = ExchangeTypes.DIRECT), value = @Queue, key = "foo01"))
    public void receiveFoo01(Foo01 foo01) {
        System.out.println("receiveFoo01 receive message: " + foo01);
    }

    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(name = DIRECT_EXCHANGE_NAME, type = ExchangeTypes.DIRECT), value = @Queue, key = "foo02"))
    public void receiveFoo02(Foo02 foo02) {
        System.out.println("receiveFoo02 receive message: " + foo02);
    }
}
