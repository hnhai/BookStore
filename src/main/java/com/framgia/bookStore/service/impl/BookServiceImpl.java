package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.entity.BookEntity;
import com.framgia.bookStore.repository.BookReponsitory;
import com.framgia.bookStore.service.BookSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<BookEntity> getTop5Book() {
        return bookReponsitory.getTop5Book(PageRequest.of(0, 4));
    }

    @Override
    public List<BookEntity> get10Books() {
        return bookReponsitory.get10Books(PageRequest.of(0, 10));
    }
}
