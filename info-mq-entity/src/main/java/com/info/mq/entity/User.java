package com.info.mq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
}
