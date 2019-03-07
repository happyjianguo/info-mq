package com.info.mq.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xxy
 * @ClassName DataUtil
 * @Description todo
 * @Date 2019/3/6 14:55
 **/
public class DateUtil {

    public static String getParaseDate(Date date ,String type){
        SimpleDateFormat sdf = new SimpleDateFormat(type);
       return sdf.format(date);
    }
}
