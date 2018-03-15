package com.example.demo.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

@Component
public class SendMessageStatusCallback implements ConfirmCallback, ReturnCallback {

	private final RabbitTemplate rabbitTemplate;

	public SendMessageStatusCallback(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
		rabbitTemplate.setConfirmCallback(this);
		rabbitTemplate.setReturnCallback(this);

	}

	/**
	 * 此方法只能说明消息成功的到达交换机
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if (ack) {
			// ACK=true仅仅标示消息已被Broker接收到，并不表示已成功投放至消息队列中
			// ACK=false标示消息由于Broker处理错误，消息并未处理成功
			System.out.println(String.format("[%s] message send successful!", correlationData));
		} else {
			// 发送到一个不存在的exchange，则会触发发布确认
			System.err.println(String.format("[%s] message send failed!", correlationData));
		}
	}

	/**
	 * 此方法只能说明消息成功的入队
	 * 
	 * @param message
	 * @param replyCode
	 * @param replyText
	 * @param exchange
	 * @param routingKey
	 */
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		// 当消息发送出去找不到对应路由队列时，将会把消息退回
		// 如果有任何一个路由队列接收投递消息成功，则不会退回消息
		System.out.println("发送消息被退回....");
		RepublishMessageRecoverer re = new RepublishMessageRecoverer(rabbitTemplate,
				message.getMessageProperties().getReceivedExchange(),
				message.getMessageProperties().getReceivedRoutingKey());
		re.recover(message, new Exception(replyText));
	}

}
