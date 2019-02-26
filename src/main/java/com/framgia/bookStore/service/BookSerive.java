package com.framgia.bookStore.service;

import com.framgia.bookStore.entity.BookEntity;
import com.framgia.bookStore.form.AddBook;
import com.framgia.bookStore.form.BookCart;
import com.framgia.bookStore.form.EditBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BookSerive {
    List<BookEntity> loadAll();
    BookEntity getByAliasName(String aliasName);
    List<BookEntity> getTop5Book();
    List<BookEntity> get10Books();
    Page<BookEntity> findAll(Pageable pageable);
    Page<BookEntity> findBook(Pageable pageable, String name, String author, String price1, String price2);
    BookCart getBook(Long id);
    BookEntity getBookById(Long id);
    Boolean addBook(AddBook addBook);
    Boolean checkBook(String alias);
    BookEntity getById(Long id);
    Boolean editBook(EditBook editBook);
    Boolean deleteBookByIds(List<Long> ids);
}
