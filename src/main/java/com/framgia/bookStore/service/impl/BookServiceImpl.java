package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.entity.BookEntity;
import com.framgia.bookStore.repository.BookReponsitory;
import com.framgia.bookStore.service.BookSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Page<BookEntity> findAllByName(Pageable pageable, String name) {
        return bookReponsitory.getByName(pageable, name);
    }

    @Override
    public Page<BookEntity> findAllByPrice(Pageable pageable, Long num1, Long num2) {
        return bookReponsitory.getAllByPriceBetween(pageable, num1, num2);
    }

    @Override
    public Page<BookEntity> findAllByAuthor(Pageable pageable, String author) {
        return bookReponsitory.getByAuthor(pageable, author);
        //return null;
    }
}
