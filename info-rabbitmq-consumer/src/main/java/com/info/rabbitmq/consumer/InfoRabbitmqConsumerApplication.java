package com.info.rabbitmq.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InfoRabbitmqConsumerApplication {

    public static void main(String[] args) {
        System.out.println("InfoRabbitmqConsumerApplication run start .........");
        SpringApplication.run(InfoRabbitmqConsumerApplication.class, args);
        System.out.println("InfoRabbitmqConsumerApplication run end .........");

    }

}
