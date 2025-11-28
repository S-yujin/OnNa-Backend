package com.onna.onnaback.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")   // Vite dev 서버 주소
public class HelloController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello from Onna backend!";
    }
}
