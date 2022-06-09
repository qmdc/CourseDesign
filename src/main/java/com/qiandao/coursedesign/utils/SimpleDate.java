package com.qiandao.coursedesign.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDate {

    //获取时间
    public static String nowtime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}
