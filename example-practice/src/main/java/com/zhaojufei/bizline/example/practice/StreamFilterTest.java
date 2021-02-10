package com.zhaojufei.bizline.example.practice;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class StreamFilterTest {
    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(1, 2, 3);
        System.out.println(list.stream().filter(num -> num > 2).collect(Collectors.toList()));
    }
}
