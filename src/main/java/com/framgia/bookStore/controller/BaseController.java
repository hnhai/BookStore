package com.framgia.bookStore.controller;

import com.framgia.bookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
    @Autowired
    protected ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    protected UserService userService;

    @Autowired
    @Qualifier("customUserDetailsService")
    protected UserDetailsService userDetailsService;
}
