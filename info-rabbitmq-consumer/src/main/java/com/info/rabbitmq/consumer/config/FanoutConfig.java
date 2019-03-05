package com.info.rabbitmq.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author xxy
 * @ClassName FanoutConfig
 * @Description todo
 * @Date 2019/3/5 10:46
 **/
@Component
public class FanoutConfig {

    @Bean
    public Queue reportPaymentQueue() {
        System.out.println("FanoutConfig -- reportPaymentQueue");
        return new Queue("api.report.payment");
    }

    @Bean
    public Queue reportRefundQueue() {
        System.out.println("FanoutConfig -- reportRefundQueue");
        return new Queue("api.report.refund");
    }

    @Bean
    public FanoutExchange reportExchange() {
        System.out.println("FanoutConfig -- reportExchange");
        return new FanoutExchange("reportExchange");
    }

    @Bean
    public Binding bindingReportPaymentExchange(Queue reportPaymentQueue, FanoutExchange reportExchange) {
        System.out.println("FanoutConfig ----- bindingReportPaymentExchange");
        return BindingBuilder.bind(reportPaymentQueue).to(reportExchange);
    }

    @Bean
    public Binding bindingReportRefundExchange(Queue reportRefundQueue, FanoutExchange reportExchange) {
        System.out.println("FanoutConfig----- bindingReportRefundExchange");
        return BindingBuilder.bind(reportRefundQueue).to(reportExchange);
    }
}
