package com.framgia.bookStore.controller;

import com.framgia.bookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
@Scope("request")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String users(Model model,@SortDefault("id") Pageable pageable){
        pageable = createPageRequest(pageable);
        model.addAttribute("users", userService.findAll(pageable));
        return "/admin/admin/users";
    }
}
