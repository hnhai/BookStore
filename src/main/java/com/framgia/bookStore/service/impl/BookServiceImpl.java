package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.entity.BookEntity;
import com.framgia.bookStore.form.BookCart;
import com.framgia.bookStore.repository.BookReponsitory;
import com.framgia.bookStore.service.BookSerive;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class BookServiceImpl implements BookSerive {

    @Autowired
    private BookReponsitory bookReponsitory;

    @Override
    public List<BookEntity> loadAll() {
        return bookReponsitory.findAll();
    }

    @Override
    public BookEntity getByAliasName(String aliasName) {
        return bookReponsitory.getByAliasName(aliasName);
    }

    @Override
    public List<BookEntity> getTop5Book() {
        return bookReponsitory.getTop5Book(PageRequest.of(0, 4));
    }

    @Override
    public List<BookEntity> get10Books() {
        return bookReponsitory.get10Books(PageRequest.of(0, 10));
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookReponsitory.findAll(pageable);
    }

    @Override
    public Page<BookEntity> findBook(Pageable pageable, String name, String author, String price1, String price2) {
        if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(author) && StringUtils.isNotBlank(price1) && StringUtils.isNotBlank(price2)){
            return bookReponsitory.getByNameAuthorAndPrice(pageable, name, author, Long.valueOf(price1), Long.valueOf(price2));
        }else if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(author)){
            return bookReponsitory.getByNameAuthor(pageable, name, author);
        }else if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(price1) && StringUtils.isNotBlank(price2)){
            return bookReponsitory.getByNamePrice(pageable, name, Long.valueOf(price1), Long.valueOf(price2));
        }else if(StringUtils.isNotBlank(author) && StringUtils.isNotBlank(price1) && StringUtils.isNotBlank(price2)){
            return bookReponsitory.getByAuthorPirce(pageable, author, Long.valueOf(price1), Long.valueOf(price2));
        }else if (StringUtils.isNotBlank(name)){
            return bookReponsitory.getByName(pageable, name);
        }else if(StringUtils.isNotBlank(author)){
            return bookReponsitory.getByAuthor(pageable, author);
        }else if(StringUtils.isNotBlank(price1) && StringUtils.isNotBlank(price2)){
            return bookReponsitory.getAllByPriceBetween(pageable, Long.valueOf(price1), Long.valueOf(price2));
        }
        return bookReponsitory.findAll(pageable);
    }

    @Override
    public BookCart getBook(Long id) {
        BookEntity bookEntity = bookReponsitory.getByDeletedAndId(false, id);
        BookCart bookCart = new BookCart(bookEntity, 1);
        return bookCart;
    }

    @Override
    public BookEntity getBookById(Long id) {
        return bookReponsitory.getByDeletedAndId(false, id);
    }
}
