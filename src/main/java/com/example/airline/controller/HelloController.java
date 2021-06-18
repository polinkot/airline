package com.example.airline.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/4444")
public class HelloController {

    @GetMapping
    public String hello() {
        return "Hello, world!11111";
    }
}