package com.zhaojufei.bizline.example.practice;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class DateTest {
    public static void main(String[] args) {

        Date date1 = DateUtil.parse("2021-02","yyyy-MM");
        Date date2 = DateUtil.parse("2021-02-01","yyyy-MM-dd");

        System.out.println(date1);
        System.out.println(date2);

        Date date3 = DateUtil.parse("2022-02-20","yyyy-MM-dd");


        System.out.println(date1.after(date2));
        System.out.println(date1.before(date2));

        System.out.println(date1.before(date3));

        System.out.println(date1.getTime());
        System.out.println(date2.getTime());


    }
}
