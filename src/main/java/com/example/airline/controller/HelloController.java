package com.example.airline.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String hello() {
        return "Hello, world!";
    }

    @GetMapping("/hello2")
    public String hello2() {
        return "Hello2, world!";
    }

    @GetMapping("/hello3")
    public String hello3() {
        return "Hello3, world!";
    }
}