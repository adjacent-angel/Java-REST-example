package com.astontech.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRest {

    @GetMapping("/test")
    public String welcomeDevs() {
        return "Welcome back developers!";
    }
}
