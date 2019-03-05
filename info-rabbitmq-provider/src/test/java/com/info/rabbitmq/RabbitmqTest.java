package com.info.rabbitmq;

import com.info.mq.entity.User;
import com.info.rabbitmq.provider.*;
import org.apache.http.client.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private HeaderSender headerSender;

    @Autowired
    private FanoutSender fanoutSender;

    @Autowired
    private DirectEntitySender directEntitySender;

    @Autowired
    private DelayedSender delayedSender;

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


    @Test
    public void test_creditBank_type() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        headerSender.creditBank(head, "银行授信(部分匹配)");
    }

    @Test
    public void test_creditBank_all() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        head.put("aging", "fast");
        headerSender.creditBank(head, "银行授信(全部匹配)");
    }

    @Test
    public void test_creditFinance_type() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        headerSender.creditFinance(head, "金融公司授信(部分匹配)");
    }

    @Test
    public void test_creditFinance_all() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        head.put("aging", "fast");
        headerSender.creditFinance(head, "金融公司授信(全部匹配)");
    }


    @Test
    public void test_fanout(){
        fanoutSender.generateReports("开始生成报表了。。。");
    }

    @Test
    public void test_directEntity(){
        User user = new User();
        user.setId(12);
        user.setName("testEntity");
        user.setAmount(new BigDecimal(123));
        user.setCreateTime(new Date());
        directEntitySender.directEntitySend(user);
    }

    @Test
    public void test_delayTime(){
        String msg = "延迟发送测试";
        delayedSender.sendDelayed(msg,50000);
    }
}
