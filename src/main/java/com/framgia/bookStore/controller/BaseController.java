package com.framgia.bookStore.controller;

import com.framgia.bookStore.service.BookSerive;
import com.framgia.bookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
    @Autowired
    protected ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    protected UserService userService;

    @Autowired
    protected BookSerive bookSerive;

    protected PageRequest createPageRequest(Pageable pageable){
        Integer pageSize = 10;
        if(pageable.getPageSize() != 1){
            pageSize = pageable.getPageSize();
        }
        return PageRequest.of(pageable.getPageNumber(), pageSize, pageable.getSort());
    }
}
