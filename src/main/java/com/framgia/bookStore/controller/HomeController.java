package com.framgia.bookStore.controller;

import com.framgia.bookStore.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController extends BaseController{

    @ModelAttribute("top5Books")
    Page<BookEntity> getTop5Books(){
        return bookSerive.getTop5Book();
    }

    @GetMapping("/")
    public String home(){
        return "user/home";
    }

    @GetMapping("/books")
    public String books(){
        return "user/books";
    }

    @GetMapping("/book/{aliasName}")
    public String book(@PathVariable("aliasName") String aliasName, Model model){
        BookEntity book = bookSerive.getByAliasName(aliasName);
        model.addAttribute("book", book);
        return "user/book";
    }

    @GetMapping("admin/view")
    public String adminHome(){
        return "admin/blank";
    }
}
