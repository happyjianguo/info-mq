package com.info.rabbitmq.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xxy
 * @ClassName TopicConfig
 * @Description
 * 配置一个routingKey为api.core的消息队列并绑定在coreExchange交换机上（交换机的匹配规则为api.core.*）
 * 配置一个routingKey为api.payment的消息队列并绑定在paymentExchange交换机上（交换机的匹配规则为api.payment.#）
 * 问题：
 * 为什么api.core.user.query发送的消息没有被api.core队列监听消费？
 * 答：因为在TopicConfig配置类中，我们对api.core队列绑定的交换机规则是api.core.*，而通配符“*”只能向后多匹配一层路径。
 *
 * TopicExchange交换机支持使用通配符 *、#
 * ‘* ’号只能向后多匹配一层路径。
 * ‘# ’号可以向后匹配多层路径。
 *
 *
  * @Author xxy
  * @Description
  * @Date 18:01 2019/3/4
  * @Param
  * @return
  **/

@Configuration
public class TopicConfig {
    @Bean
    public Queue apiCoreQueue() {
        System.out.println("consumer  -- topicConfig -- coreQueue -- api.core");
        return new Queue("api.core");
    }

    @Bean
    public Queue paymentQueue() {
        System.out.println("consumer  -- topicConfig -- paymentQueue -- api.payment");
        return new Queue("api.payment");
    }

    @Bean
    public TopicExchange coreExchange() {
        System.out.println("consumer  -- topicConfig -- coreExchange -- coreExchange");
        return new TopicExchange("coreExchange");
    }

    @Bean
    public TopicExchange paymentExchange() {
        System.out.println("consumer  -- topicConfig -- paymentExchange -- paymentExchange");
        return new TopicExchange("paymentExchange");
    }


    @Bean
    public Binding bindingCoreExchange(Queue apiCoreQueue, TopicExchange coreExchange) {
        System.out.println("consumer  -- topicConfig -- bindingCoreExchange -- api.core.*");
        return BindingBuilder.bind(apiCoreQueue).to(coreExchange).with("api.core.*");
    }

    @Bean
    public Binding bindingPaymentExchange(Queue paymentQueue, TopicExchange paymentExchange) {
        System.out.println("consumer  -- topicConfig -- bindingPaymentExchange -- api.payment.#");
        return BindingBuilder.bind(paymentQueue).to(paymentExchange).with("api.payment.#");
    }


}
