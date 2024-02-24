package com.zw.graducate.consts;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/2/4 20:17
 */

@Configuration
public class MqConfig {

//    定义交换机
    @Bean
    public TopicExchange orderExchange(){
        return new TopicExchange(MqConstants.ORDER_EXCHANGE,true,false);
    }

//    定义队列
    @Bean
    public Queue orderInfoSelectQueue(){
        return new Queue(MqConstants.ORDER_SELINFO_QUEUE,true);
    }

    @Bean
    public Binding selectOrderInfoQueueBinding(){
        return BindingBuilder.bind(orderInfoSelectQueue()).to(orderExchange()).with(MqConstants.ORDER_SELINFO_KEY);
    }
}
