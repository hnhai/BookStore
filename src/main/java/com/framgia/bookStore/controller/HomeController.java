package com.framgia.bookStore.controller;

import com.framgia.bookStore.entity.BookEntity;
import com.framgia.bookStore.entity.UserEntity;
import com.framgia.bookStore.form.BookCart;
import com.framgia.bookStore.util.SecurityUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
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
        Long totalPrice = new Long(0);
        if(cart != null) {
            for (BookCart b : cart) {
                totalPrice += (b.getBook().getPrice() * b.getQuantity());
            }
        }
        model.addAttribute("totalPrice", session.getAttribute("totalPrice"));
        model.addAttribute("cart", cart);
        return "user/cart";
    }

    @GetMapping("/orders")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public String goOrders(Model model, @SortDefault("id") Pageable pageable){
        UserEntity user = userService.findByUsername(SecurityUtil.getCurrentUser().getUsername());
        pageable = PageRequest.of(pageable.getPageNumber(), 20, pageable.getSort());
        model.addAttribute("orders", orderService.loadAllByUser(pageable, user));
        return "user/orders";
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_EMPLOYEE', 'ROLE_ADMIN')")
    public String goProfile(Model model){
        UserEntity user = userService.findByUsername(SecurityUtil.getCurrentUser().getUsername());
        model.addAttribute("user", user);
        return "/user/profile";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_EMPLOYEE')")
    @GetMapping("/order/{id}")
    public String orderDetail(@PathVariable("id") Long id, Model model){
        if(!orderService.canViewOrder(id)){
            return "redirect:/404";
        }
        model.addAttribute("orderDetails", orderService.loadDetail(id));
        return "/user/order";
    }
}
