package com.info.rabbitmq;

import com.info.rabbitmq.provider.DirectSender;
import com.info.rabbitmq.provider.TopicSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author xxy
 * @ClassName RabbitmqTest
 * @Description todo
 * @Date 2019/3/4 15:54
 **/
@SpringBootTest(classes=InfoRabbitmqApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitmqTest {

    @Autowired
    private DirectSender directSender;

    @Autowired
    private TopicSender topicSender;

    @Test
    public void testDirectMsg() {
       directSender.sendDirectMsg("当前时间为："+System.currentTimeMillis());
    }


    @Test
    public void test_user() {
        topicSender.user("用户管理！");
    }

    @Test
    public void test_userQuery() {
        topicSender.userQuery("查询用户信息！");
    }


    @Test
    public void test_order() {
        topicSender.order("订单管理！");
    }

    @Test
    public void test_orderQuery() {
        topicSender.orderQuery("查询订单信息！");
    }

    @Test
    public void test_orderDetailQuery() {
        topicSender.orderDetailQuery("查询订单详情信息！");
    }
}
