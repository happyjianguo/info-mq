package com.info.rabbitmq.consumer.config;

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
@Configuration
public class DelayedConfig {

    public final static String QUEUE_NAME = "delayed.test.queue";
    public final static String EXCHANGE_NAME = "delayed.test.exchange";

    @Bean
    public Queue queue() {
        System.out.println("初始化queue...");
        return new Queue(DelayedConfig.QUEUE_NAME,true);
    }


    @Bean
    CustomExchange customExchange() {
        System.out.println("转换customExchange....");
        Map<String, Object> args = new HashMap<>(12);
        args.put("x-delayed-type", "direct");
        //参数二为类型：必须是x-delayed-message
        return new CustomExchange(DelayedConfig.EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }


    @Bean
    Binding binding(Queue queue, CustomExchange exchange) {
        System.out.println("绑定binding。。。。。。");
        return BindingBuilder.bind(queue).to(exchange).with(DelayedConfig.QUEUE_NAME).noargs();
    }
}
