package com.framgia.bookStore.controller;

import com.framgia.bookStore.entity.BookEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController extends BaseController{

    private static final Logger LOGGER = LogManager.getLogger(HomeController.class);

    @ModelAttribute("top5Books")
    List<BookEntity> getTop5Books(){
        return bookSerive.getTop5Book();
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("get10Books", bookSerive.get10Books());
        return "user/home";
    }

    @GetMapping("/books")
    public String books(Model model, @SortDefault("id") Pageable pageable, @RequestParam(value = "name", required = false) String name,
                        @RequestParam(value = "author", required = false) String author,
                        @RequestParam(value = "price", required = false) String price){
        pageable = createPageRequest(pageable);
        Page<BookEntity> books = null;
        if(!StringUtils.isEmpty(name)){
            books = bookSerive.findAllByName(pageable, name);
        }else if (!StringUtils.isEmpty(author)){
            books = bookSerive.findAllByAuthor(pageable, author);
        }else if(!StringUtils.isEmpty(price)){
            String prices [] = price.split("-");
            try {
                books = bookSerive.findAllByPrice(pageable, Long.valueOf(prices[0]), Long.valueOf(prices[1]));
            }catch (Exception e){
                LOGGER.error(e);
                books = bookSerive.findAll(pageable);
            }
        }else{
            books = bookSerive.findAll(pageable);
        }
        model.addAttribute("books", books);
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
