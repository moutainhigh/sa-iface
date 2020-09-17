package com.ai.cloud.tool;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.ai.cloud.constant.MqQueueConstant.EXCHANGE_NAME;

@Component
public class RabbitMqSender implements RabbitTemplate.ConfirmCallback {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
    }

    /**
     * 所有发送到Topic Exchange的消息被转发到所有关心RouteKey中指定Topic的Queue上
     *
     * @param routeKey
     * @param obj
     */
    public void sendRabbitmqTopic(String routeKey, Object obj) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        this.rabbitTemplate.convertAndSend(EXCHANGE_NAME, routeKey, obj, correlationData);
    }

    /**
     * 直发交换机
     *
     * @param routeKey
     * @param obj
     */
    public void sendDefaultRabbitMq(String routeKey, Object obj) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        this.rabbitTemplate.convertAndSend(routeKey, obj, correlationData);
    }
}
