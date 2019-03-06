package com.framgia.bookStore.controller;

import com.framgia.bookStore.form.Register;
import com.framgia.bookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("admin")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String users(Model model,@SortDefault("id") Pageable pageable){
        pageable = createPageRequest(pageable);
        model.addAttribute("users", userService.findAll(pageable));
        return "/admin/admin/users";
    }

    @PostMapping("/users")
    @ResponseBody
    public ResponseEntity deleteUsers(@RequestParam("ids[]") List<Long> ids){
        return new ResponseEntity(userService.deleteAllById(ids), HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity registerProcess(@Valid Register form, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            new ResponseEntity(Boolean.FALSE, HttpStatus.OK);
        }
        return new ResponseEntity(userService.saveUser(form, request), HttpStatus.OK);
    }

    @GetMapping("/chart")
    public String report(Model model){
        return "/admin/admin/report";
    }
}
