package com.saurabh.smartrecruit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/health")
    public String health() {
        return "OK";
    }

    @GetMapping("/api/test")
    public String test() {
        return "Protected API working successfully";
    }

    @PostMapping("/api/test-post")
    public String testPost() {
        return "POST API working successfully";
    }
}
