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

    @GetMapping("/hello3")
    public String hello3() {
        return "Hello3, world!";
    }

    @GetMapping("/hello4")
    public String hello4() {
        return "Hello4, world!";
    }
}