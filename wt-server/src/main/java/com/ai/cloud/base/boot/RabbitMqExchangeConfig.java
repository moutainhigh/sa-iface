package com.ai.cloud.base.boot;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.ai.cloud.constant.MqQueueConstant.EXCHANGE_NAME;
import static com.ai.cloud.constant.MqQueueConstant.SCAN_MESSAGE_NOTICE;

@Configuration
@AutoConfigureAfter(RabbitClusterConfig.class)
public class RabbitMqExchangeConfig {


    /**
     * @param
     * @return
     * @Description: 主题型交换机
     */
    @Bean
    TopicExchange contractTopicExchangeDurable(RabbitAdmin rabbitAdmin) {
        TopicExchange contractTopicExchange = new TopicExchange(EXCHANGE_NAME);
        rabbitAdmin.declareExchange(contractTopicExchange);
        return contractTopicExchange;
    }

    /**
     * 扫码消息推送
     *
     * @param rabbitAdmin
     * @return
     */
    @Bean
    Queue queueScanMessageNotice(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(SCAN_MESSAGE_NOTICE);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

}
