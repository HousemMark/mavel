package com.skyline.merchant.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merchant")
public class UserController {

    @PostMapping("/echo")
    public String echo(@RequestParam("var") String var){

        return var;
    }
}
