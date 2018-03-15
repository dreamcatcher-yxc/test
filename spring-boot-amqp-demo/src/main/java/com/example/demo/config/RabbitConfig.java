package com.example.demo.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String FNAOUT_EXCHANGE_NAME = "test.fanout";

    public static final String DIRECT_EXCHANGE_NAME = "test.direct";

	/**
	 * 声明一个 fanout 类型的交换机
	 */
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(FNAOUT_EXCHANGE_NAME);
	}

    /**
     * 声明一个为 direct 类型的交换机
     * @return
     */
	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange(DIRECT_EXCHANGE_NAME);
	}
}
