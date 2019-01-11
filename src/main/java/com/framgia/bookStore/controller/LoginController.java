package com.framgia.bookStore.controller;

import com.framgia.bookStore.dto.user.RegisterForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController extends BaseController{

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

    @GetMapping("/register")
    public String registerView(){
        return "/register";
    }

    @PostMapping("/register")
    public String registerProcess(@Valid RegisterForm form, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/system";
        }
        if(userService.saveUser(form)){
            return "/admin";
        }
        return "/home";
    }
}
