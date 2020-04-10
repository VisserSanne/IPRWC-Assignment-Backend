package com.example.RelaxWithGems.Models;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
