package com.qiandao.coursedesign.utils;


import java.util.UUID;

public class Uuid {

    public static String getUUID() {
        //返回uuid的前十五位
        String uuid = UUID.randomUUID().toString();
        String replace = uuid.replace("-", "");
        return replace.substring(15);
    }
}
