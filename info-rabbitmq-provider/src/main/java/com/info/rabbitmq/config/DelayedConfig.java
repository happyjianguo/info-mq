package com.info.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxy
 * @ClassName DelayedConfig
 * @Description todo
 * @Date 2019/3/5 16:12
 **/
public class DelayedConfig {

    public final static String QUEUE_NAME = "delayed.test.queue";
    public final static String EXCHANGE_NAME = "delayed.test.exchange";

//    @Bean
//    public Queue queue() {
//        return new Queue(DelayedConfig.QUEUE_NAME,true);
//    }
//
//    // 配置默认的交换机
//    @Bean
//    CustomExchange customExchange() {
//        Map<String, Object> args = new HashMap<>(12);
//        args.put("x-delayed-type", "direct");
//        //参数二为类型：必须是x-delayed-message
//        return new CustomExchange(DelayedConfig.EXCHANGE_NAME, "x-delayed-message", true, false, args);
//    }
//
//    // 绑定队列到交换器
//    @Bean
//    Binding binding(Queue queue, CustomExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(DelayedConfig.QUEUE_NAME).noargs();
//    }
}
