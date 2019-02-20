package com.framgia.bookStore.controller;

import com.framgia.bookStore.entity.BookEntity;
import com.framgia.bookStore.form.BookCart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController extends BaseController{

    @GetMapping(path = "/add-to-card/{id}")
    public String addToCard(@PathVariable("id") Long id, HttpSession session){
        if(session.getAttribute("cart") == null){
            List<BookCart> cart = new ArrayList<>();
            BookCart book = bookSerive.getBook(id);
            cart.add(book);
            session.setAttribute("cart", cart);
            session.setAttribute("totalPrice", book.getBook().getPrice() * book.getQuantity());
        }else {
            List<BookCart> cart = (List<BookCart>) session.getAttribute("cart");
            int index = this.exists(id, cart);
            if(index == -1){
                cart.add(bookSerive.getBook(id));
            }else{
                int quantity = cart.get(index).getQuantity() + 1;
                cart.get(index).setQuantity(quantity);
            }
            Long totalPrice = new Long(0);
            for (BookCart b: cart) {
                totalPrice += (b.getBook().getPrice() * b.getQuantity());
            }
            session.setAttribute("totalPrice", totalPrice);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    @GetMapping(path = "/delete-cart-item/{id}")
    public String deleteInCart(@PathVariable("id") Long id, HttpSession session){
        List<BookCart> cart = (List<BookCart>) session.getAttribute("cart");
        int index = this.exists(id, cart);
        cart.remove(index);
        Long totalPrice = new Long(0);
        for (BookCart b: cart) {
            totalPrice += (b.getBook().getPrice() * b.getQuantity());
        }
        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping(path = "/delete-all-cart")
    public String deleteAllCart(HttpSession session){
        List<BookCart> cart = null;
        session.setAttribute("totalPrice", 0);
        session.setAttribute("cart", cart);
        return "redirect:/";
    }

    @GetMapping(path = "/update-cart/{id}/{quantity}")
    public String updateCart(HttpSession session, @PathVariable("id") Long id, @PathVariable("quantity") Integer quantity){
        List<BookCart> cart = (List<BookCart>) session.getAttribute("cart");
        BookEntity book = bookSerive.getBookById(id);
        if(quantity <= 0 || quantity > book.getQuantity()){
            return "redirect:/cart?invalidQuantity";
        }else{
            int index = this.exists(id, cart);
            cart.get(index).setQuantity(quantity);
            Long totalPrice = new Long(0);
            for (BookCart b: cart) {
                totalPrice += (b.getBook().getPrice() * b.getQuantity());
            }
            session.setAttribute("totalPrice", totalPrice);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    private int exists(Long id, List<BookCart> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getBook().getId().toString().equalsIgnoreCase(id.toString())) {
                return i;
            }
        }
        return -1;
    }
}
