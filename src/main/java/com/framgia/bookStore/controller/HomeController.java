package com.framgia.bookStore.controller;

import com.framgia.bookStore.entity.BookEntity;
import com.framgia.bookStore.form.BookCart;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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
                        @RequestParam(value = "price1", required = false) String price1,
                        @RequestParam(value = "price2", required = false) String price2){
        pageable = PageRequest.of(pageable.getPageNumber(), 20, pageable.getSort());
        Page<BookEntity> books = bookSerive.findBook(pageable, name, author, price1, price2);
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

    @GetMapping("/cart")
    public String goCart(Model model, HttpSession session){
        List<BookCart> cart = (List<BookCart>) session.getAttribute("cart");
        model.addAttribute("cart", cart);
        return "user/cart";
    }
}
