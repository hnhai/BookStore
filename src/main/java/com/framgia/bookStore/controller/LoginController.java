package com.framgia.bookStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/admin")
    public String adminView(){
        return "admin";
    }

    @GetMapping("/system")
    public String sysView(){
        return "system";
    }
}
