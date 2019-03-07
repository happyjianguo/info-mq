//package com.info.rabbitmq.consumer.rpc;
//
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
///**
// * @author xxy
// * @ClassName HeaderApiCreditReceive
// * @Description todo
// * @Date 2019/3/5 10:02
// **/
//@Component
//public class HeaderApiCreditReceive {
//
//    @RabbitHandler
//    @RabbitListener(queues = "credit.bank")
//    public void creditBank(String msg) {
//        System.out.println("credit.bank receive message: "+msg);
//    }
//
//    @RabbitHandler
//    @RabbitListener(queues = "credit.finance")
//    public void creditFinance(String msg) {
//        System.out.println("credit.finance receive message: "+msg);
//    }
//}
