package com.example.demo;

import com.example.demo.config.RabbitConfig;
import com.example.demo.entity.p1.Foo01;
import com.example.demo.entity.p2.Foo02;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import static com.example.demo.config.RabbitConfig.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(RabbitConfig.class)
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.example.demo.mq.*")
})
public class Test01 {

    @Autowired
    private RabbitTemplate template;

    @Test
    public void testSendFanoutMsg() {
        template.convertAndSend(FNAOUT_EXCHANGE_NAME, null,"hello");
    }

    @Test
    public void testSendDirectMsg() {
        template.convertAndSend(DIRECT_EXCHANGE_NAME, "warn","this is a warn msg!!!");
        template.convertAndSend(DIRECT_EXCHANGE_NAME, "error","this is a error msg!!!");
    }

    /**
     * 测试发送对象消息
     */
    @Test
    public void testSendObjectMsg() {
        Foo01 foo01 = new Foo01();
        foo01.setFoo01Prop01("foo-01-prop-01");
        foo01.setFoo02Prop02("foo-01-prop-02");

        Foo02 foo02 = new Foo02();
        foo02.setFoo02Prop01("foo-02-prop-01");
        foo02.setFoo02Prop02("foo-02-prop-02");

        template.convertAndSend(DIRECT_EXCHANGE_NAME, "foo01", foo01);
        template.convertAndSend(DIRECT_EXCHANGE_NAME, "foo02", foo02);
    }
}
