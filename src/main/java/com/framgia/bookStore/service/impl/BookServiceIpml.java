package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.entity.BookEntity;
import com.framgia.bookStore.repository.BookReponsitory;
import com.framgia.bookStore.service.BookSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceIpml implements BookSerive {

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
}
