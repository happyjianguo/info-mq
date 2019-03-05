package com.info.rabbitmq.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * @author xxy
 * @ClassName SenderConf
 * @Description todo
 * @Date 2019/3/4 15:44
 **/
@Configuration
public class DirectConf {

    @Bean
    public Queue queue(){
        return new Queue("notify.payment") ;
    }
}
