package com.info.mq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xxy
 * @ClassName User
 * @Description todo
 * @Date 2019/3/5 11:39
 **/
@Data
@ToString
public class User implements Serializable {
    private Integer id;
    private String name;
    private BigDecimal amount;
    private Date createTime;


    public static List<User> buildUserList(){
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1001);
        user1.setName("开始");

        User user4 = new User();
        user4.setId(1002);
        user4.setName("开始");

        User user2 = new User();
        user2.setId(1001);
        user2.setName("构建");


        User user3 = new User();
        user3.setId(1001);
        user3.setName("结束");

        User user5 = new User();
        user5.setId(1002);
        user5.setName("结束");

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);

        return userList;


    };
}

