package com.zhaojufei.bizline.example.webapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @RequestMapping("/ping")
    public String ping() {
        return "pong";
    }

    @RequestMapping("/health")
    public String health() {
        return "pong";
    }
}
