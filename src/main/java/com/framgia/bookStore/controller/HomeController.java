package com.framgia.bookStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "user/home";
    }

    @GetMapping("/books")
    public String books(){
        return "user/books";
    }

    @GetMapping("/book")
    public String book(){
        return "user/book";
    }

    @GetMapping("admin/view")
    public String adminHome(){
        return "admin/blank";
    }
}
