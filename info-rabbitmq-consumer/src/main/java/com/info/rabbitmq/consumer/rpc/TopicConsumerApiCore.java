//package com.info.rabbitmq.consumer.rpc;
//
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
///**
// * @author xxy
// * @ClassName TopicConsumerApiCore
// * @Description todo
// * @Date 2019/3/4 17:09
// **/
//@Component
//public class TopicConsumerApiCore {
//
//    @RabbitHandler
//    @RabbitListener(queues = "api.core")
//    public void user(String msg) {
//        System.out.println("api.core receive message: "+msg);
//    }
//
//}
