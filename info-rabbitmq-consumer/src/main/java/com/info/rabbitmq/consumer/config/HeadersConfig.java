package com.info.rabbitmq.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxy
 * @ClassName HeadersConfig
 * @Description todo
 * @Date 2019/3/5 10:00
 **/
@Component
public class HeadersConfig {
    @Bean
    public Queue creditBankQueue() {
        return new Queue("credit.bank");
    }

    @Bean
    public Queue creditFinanceQueue() {
        return new Queue("credit.finance");
    }

    @Bean
    public HeadersExchange creditBankExchange() {
        return new HeadersExchange("creditBankExchange");
    }

    @Bean
    public HeadersExchange creditFinanceExchange() {
        return new HeadersExchange("creditFinanceExchange");
    }

    /**
     * @Author xxy
     * @Description
     * @Date 10:22 2019/3/5 whereAll 全匹配 
     * @Param [creditBankQueue, creditBankExchange]
     * @return org.springframework.amqp.core.Binding
     **/
    @Bean
    public Binding bindingCreditAExchange(Queue creditBankQueue, HeadersExchange creditBankExchange) {
        Map<String,Object> headerValues = new HashMap<>(2);
        headerValues.put("type", "cash");
        headerValues.put("aging", "fast");
        return BindingBuilder.bind(creditBankQueue).to(creditBankExchange).whereAll(headerValues).match();
        //BindingBuilder.bind(creditBankQueue).to(creditBankExchange).where
    }

    /**
     * @Author xxy
     * @Description  whereAny  部分匹配
     * @Date 10:23 2019/3/5
     * @Param [creditFinanceQueue, creditFinanceExchange]
     * @return org.springframework.amqp.core.Binding
     **/
    @Bean
    public Binding bindingCreditBExchange(Queue creditFinanceQueue, HeadersExchange creditFinanceExchange) {
        Map<String,Object> headerValues = new HashMap<>(2);
        headerValues.put("type", "cash");
        headerValues.put("aging", "fast");
        return BindingBuilder.bind(creditFinanceQueue).to(creditFinanceExchange).whereAny(headerValues).match();
    }
}
