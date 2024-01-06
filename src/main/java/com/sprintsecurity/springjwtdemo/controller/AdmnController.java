package com.sprintsecurity.springjwtdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdmnController {

    @GetMapping
    public String hell(){
        return "Hello yenosh";
    }
    
}
