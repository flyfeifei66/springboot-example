package com.zhaojufei.bizline.example.practice;

import lombok.Data;

public class FxTest {
    public static void main(String[] args) {

        Age age = new Age();
        age.setNumber(1);

        FX fx = new FX();
        fx.setName("hh");
        fx.setContent(age);

        System.out.println(fx);
    }

    public static void ss(int k) {
        int i = 1;
        System.out.println(++i);
        System.out.println(i);

    }


    @Data
    public static class FX<T> {
        private String name;
        private T content;
    }


    @Data
    public static class Age {
        private Integer number;
    }
}
