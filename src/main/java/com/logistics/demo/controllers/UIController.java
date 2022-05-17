package com.logistics.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UIController {
    
    @RequestMapping("/")
    public String homeRoute() {
        return "index";
    }
}
