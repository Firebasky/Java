package com.firebasky.echo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demo {
    @RequestMapping("/demo")
    public void demo() throws InterruptedException {
        new Alltomcat();
    }
}
