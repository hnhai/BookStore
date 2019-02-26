package com.framgia.bookStore.controller;

import com.framgia.bookStore.entity.AuthorEnity;
import com.framgia.bookStore.entity.BookEntity;
import com.framgia.bookStore.entity.CategoryEntity;
import com.framgia.bookStore.entity.PublisherEntity;
import com.framgia.bookStore.form.AddBook;
import com.framgia.bookStore.form.EditBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController extends BaseController{

    @ModelAttribute("categories")
    List<CategoryEntity> getCategorys(){return categoryService.loadAll();}

    @ModelAttribute("publishers")
    List<PublisherEntity> getPublishers(){return publisherService.loadAll();}

    @ModelAttribute("authors")
    List<AuthorEnity> getAuthors(){return authorService.loadAll();}

    @GetMapping("/books")
    public String showBooks(Model model, @SortDefault("id") Pageable pageable){
        pageable = pageable = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());
        Page<BookEntity> books = bookSerive.findBook(pageable, null, null, null, null);
        model.addAttribute("books", books);
        return "admin/employee/books";
    }

    @PostMapping("/addBook")
    public String addBook(@Valid AddBook addBook){
        if(bookSerive.addBook(addBook)){
            return "redirect:/employee/books";
        }
        return "redirect:/employee/books?addError";
    }

    @PostMapping("/editBook")
    public String editBook(@Valid EditBook editBook){
        if(bookSerive.editBook(editBook)){
            return "redirect:/employee/books";
        }
        return "redirect:/employee/books?addError";
    }

    @GetMapping("/api-check-book-name/{alias}")
    @ResponseBody
    public ResponseEntity checkName(@PathVariable("alias") String alias){
        return new ResponseEntity<>(bookSerive.checkBook(alias), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public String orders(Model model, @SortDefault("id") Pageable pageable){
        pageable = pageable = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());
        model.addAttribute("orders", orderService.loadAll(pageable));
        return "admin/employee/orders";
    }

    @PostMapping("/order/{id}/{status}")
    @ResponseBody
    public ResponseEntity updateOrder(@PathVariable("id") Long id, @PathVariable("status") Integer status){
        return new ResponseEntity(orderService.updateOrder(id, status), HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable("id") Long id, Model model){
        model.addAttribute("book", bookSerive.getById(id));
        return "admin/employee/book";
    }

    @PostMapping("/books")
    @ResponseBody
    public ResponseEntity deleteUsers(@RequestParam("ids[]") List<Long> ids){
        return new ResponseEntity(bookSerive.deleteBookByIds(ids), HttpStatus.OK);
    }
}
