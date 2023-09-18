package com.zhaojufei.bizline.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zhaojufei.bizline.example.core.constant.AdviceOrder;

import lombok.extern.slf4j.Slf4j;

/**
 * 应用启动类
 * @author zhaojufei
 */
@SpringBootApplication
@EnableTransactionManagement(order = AdviceOrder.DB_TRANSACTION)
//@EnableFeignClients
@Slf4j
public class ExampleWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleWebApplication.class, args);
		System.out.println("------------------启动完成------------------");
	}

}
