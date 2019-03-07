//package com.info.rabbitmq.consumer.rpc;
//
//import org.springframework.amqp.core.ExchangeTypes;
//import org.springframework.amqp.rabbit.annotation.*;
//import org.springframework.stereotype.Component;
//
///**
// * @author xxy
// * @ClassName RabbitmqAnnotationTest
// * @Description todo
// * @Date 2019/3/6 10:02
// **/
//
//@Component
//@RabbitListener(bindings = @QueueBinding(
//        value=@Queue(value = "${mq.config.queue}",autoDelete = "false"),
//        exchange = @Exchange(value = "${mq.config.exchange}",type = ExchangeTypes.DIRECT),
//        key = "${mq.config.info.key}"))
//public class RabbitmqAnnotationConsumer {
//    @RabbitHandler
//    public void annotationConsumer(String msg){
//        System.out.println("annotation receive ....." + msg);
//        //throw new RuntimeException("消费失败！！！！！");
//    }
//}
