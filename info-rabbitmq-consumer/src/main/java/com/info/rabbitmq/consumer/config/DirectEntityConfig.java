package com.info.rabbitmq.consumer.config;

import jdk.internal.util.xml.impl.ReaderUTF8;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xxy
 * @ClassName DirectEntityConfig
 * @Description todo
 * @Date 2019/3/5 14:16
 **/
@Configuration
public class DirectEntityConfig {

    @Bean
    public Queue directEntityQueue(){
        System.out.println("初始化DirectEntityConfig。。。。。");
        return  new Queue("direct.entity");
    }
}
