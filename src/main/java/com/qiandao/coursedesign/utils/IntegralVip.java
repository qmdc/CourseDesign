package com.qiandao.coursedesign.utils;

import com.qiandao.coursedesign.config.UserRealm;

import java.math.BigDecimal;

public class IntegralVip {

    //积分增加方法
    public static int integral(String price) {
        return (int)Double.parseDouble(price) /10;
    }

    //积分折扣方法
    public static BigDecimal money(int integral) {
        return BigDecimal.valueOf(0.8).multiply(BigDecimal.valueOf(integral));
    }

    //用户折扣等级
    public static double discount(int userGrade) {
        double discount = 1;
        switch (userGrade){
            case 0 : discount = 0.98; break;
            case 1 : discount = 0.96; break;
            case 2 : discount = 0.94; break;
            case 3 : discount = 0.92; break;
            case 4 : discount = 0.90; break;
            case 5 : discount = 0.88; break;
            case 6 : discount = 0.86; break;
            case 7 : discount = 0.84; break;
            case 8 : discount = 0.82; break;
            case 9 : discount = 0.80; break;
            case 10 : discount = 0.70; break;
            default :
                break;
        }
        return discount;
    }

    //给出用户相应等级
    public static int vip(double userSpend) {
        if (userSpend<5000) {
            return 0;
        } else if (userSpend<15000) {
            return 1;
        } else if (userSpend < 40000) {
            return 2;
        } else if (userSpend<100000) {
            return 3;
        } else if (userSpend<200000) {
            return 4;
        } else if (userSpend<500000) {
            return 5;
        } else if (userSpend < 1500000) {
            return 6;
        } else if (userSpend < 2500000) {
            return 7;
        } else if (userSpend < 4000000) {
            return 8;
        } else if (userSpend<6000000) {
            return 9;
        } else if (userSpend > 7000000) {
            return 10;
        } else {
            return 0;
        }

    }

}
